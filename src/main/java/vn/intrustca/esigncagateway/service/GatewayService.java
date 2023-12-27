package vn.intrustca.esigncagateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import vn.intrustca.esigncagateway.payload.*;
import vn.intrustca.esigncagateway.payload.request.*;
import vn.intrustca.esigncagateway.payload.response.*;
import vn.intrustca.esigncagateway.utils.DefaultValue;
import vn.intrustca.esigncagateway.utils.RestHelper;
import vn.intrustca.esigncagateway.utils.ServiceUtils;
import vn.intrustca.esigncagateway.utils.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Value("${ra.endpoint}")
    private String raEndpoint;
    @Value("${ra.username}")
    private String raUserName;
    @Value("${ra.password}")
    private String raPassword;
    @Value("${ra.key.store}")
    private String raKeyStore;
    @Value("${ra.key.store.password}")
    private String raKeyStorePassword;
    @Value("${idp.endpoint}")
    private String idpEndpoint;
    @Value("${idp.key.store}")
    private String idpKeyStore;
    @Value("${idp.key.store.password}")
    private String idpKeyStorePassword;

    public GetCertResponse getCerts(GetCertRequest request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        GetCertResponse response = new GetCertResponse();
        try {
            RaLoginRequest loginRequest = new RaLoginRequest(raUserName, raPassword);
            RestHelper restHelper = new RestHelper();
            restHelper.init(raEndpoint, raKeyStore, raKeyStorePassword);
            RaLoginResponse loginResponse = restHelper.callCaService("auth/signin", loginRequest, null, httpRequest, RaLoginResponse.class, DefaultValue.RA_SERVICE);
            if(loginResponse.getCode() == 0){
                RaGetCertRequest raGetCertRequest = new RaGetCertRequest(request.getUserId());
                List<RaUserCertificate> responseCerts = restHelper.getCerts("getcertuser", raGetCertRequest, loginResponse.getAccessToken(), httpRequest);
                response.setUserCertificates(ServiceUtils.adaptRaCertResponseToNeac(responseCerts));
                response.setTransactionId(request.getTransactionId());
            }else {
                throw new BusinessException(ExceptionCode.LOGIN_FAIL);
            }
        } catch (HttpClientErrorException | IOException | CertificateException | NoSuchAlgorithmException |
                 KeyStoreException | KeyManagementException | UnrecoverableKeyException e) {
            throw new BusinessException(ExceptionCode.CERT_NOT_FOUND);
        }
        return response;
    }

    public SignFileResponse signFile(SignFileRequest request, HttpServletRequest httpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        SignFileResponse response = new SignFileResponse();
        try {
            IdpLoginRequest loginRequest = new IdpLoginRequest(true, "SAP", DefaultValue.IDP_NEAC_DEVICE);
            RestHelper restHelper = new RestHelper();
            restHelper.init(idpEndpoint, idpKeyStore, idpKeyStorePassword);
            IdpLoginResponse loginResponse = restHelper.callCaService("users/auth/login", loginRequest, DefaultValue.IDP_NEAC_AUTH_LOGIN, httpRequest, IdpLoginResponse.class, DefaultValue.IDP_SERVICE);
            if(loginResponse.getCode() == 0){
                List<String> listHash = new ArrayList<>();
                for (File file : request.getListFiles()) {
                    listHash.add(file.getDataToBeSigned());
                }
                IdpSignFileRequest idpSignFileRequest = new IdpSignFileRequest(request.getUserId(), request.getSerialNumber(), listHash.size(), listHash, DefaultValue.KEY_PASS);
                IdpSignFileResponse responseSignatures = restHelper.signFile("users/credentials/signHashNeac", idpSignFileRequest, loginResponse.getAccessToken(), httpRequest);
                if (responseSignatures.getStatusCode() == 6) {
                    throw new BusinessException(ExceptionCode.LOGIN_FAIL);
                }
                if (responseSignatures.getStatusCode() == 400) {
                    if ("Invalid parameter credentialID".equals(responseSignatures.getErrorDesc())){
                        throw new BusinessException(ExceptionCode.INVALID_CREDENTIALS);
                    }
                    throw new BusinessException(ExceptionCode.INVALID_FORMAT_HASHCODE);
                }
                if (responseSignatures.getStatusCode() != 0) {
                    throw new BusinessException(ExceptionCode.SIGN_FAIL);
                }
                response.setSignatures(ServiceUtils.adaptIdpSignatureResponseToNeac(request, responseSignatures));
                response.setTransactionId(request.getTransactionId());
            }else {
                throw new BusinessException(ExceptionCode.LOGIN_FAIL);
            }
        } catch (HttpClientErrorException | IOException | CertificateException | NoSuchAlgorithmException |
                 KeyStoreException | KeyManagementException | UnrecoverableKeyException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private List<Signature> adaptIdpSignatureResponseToNeac (SignFileRequest request, IdpSignFileResponse responseSignatures) {
        List<Signature> listSignatures = new ArrayList<>();
        for (String signatureIdp : responseSignatures.getSignatures()) {
            Signature signature = new Signature();
            signature.setSignatureValue(signatureIdp);

            listSignatures.add(signature);
        }
        return listSignatures;
    }
}
