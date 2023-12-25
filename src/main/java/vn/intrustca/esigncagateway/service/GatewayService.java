package vn.intrustca.esigncagateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.intrustca.esigncagateway.payload.ChainData;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;
import vn.intrustca.esigncagateway.payload.UserCertificate;
import vn.intrustca.esigncagateway.payload.request.*;
import vn.intrustca.esigncagateway.payload.response.GetCertResponse;
import vn.intrustca.esigncagateway.payload.response.RaLoginResponse;
import vn.intrustca.esigncagateway.payload.response.SignFileResponse;
import vn.intrustca.esigncagateway.utils.RestHelper;
import vn.intrustca.esigncagateway.utils.ServiceUtils;
import vn.intrustca.esigncagateway.utils.exception.ServiceException;
import vn.intrustca.esigncagateway.utils.exception.ServiceExceptionBuilder;
import vn.intrustca.esigncagateway.utils.exception.ValidationError;
import vn.intrustca.esigncagateway.utils.exception.ValidationErrorResponse;

import javax.servlet.http.HttpServletRequest;
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
    @Value("${idp.username}")
    private String idpUserName;
    @Value("${idp.password}")
    private String idpPassword;
    @Value("${idp.key.store}")
    private String idpKeyStore;
    @Value("${idp.key.store.password}")
    private String idpKeyStorePassword;

    public GetCertResponse getCerts(GetCertRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        GetCertResponse response = new GetCertResponse();
        try {
            RaLoginRequest loginRequest = new RaLoginRequest(raUserName, raPassword);
            RestHelper restHelper = new RestHelper();
            restHelper.init(raEndpoint, raKeyStore, raKeyStorePassword);

            RaLoginResponse loginResponse = restHelper.callRaService("auth/signin", loginRequest, null, httpRequest, RaLoginResponse.class);
            if(loginResponse.getCode() == 0){
                RaGetCertRequest raGetCertRequest = new RaGetCertRequest(request.getUserId());
                List<RaUserCertificate> responseCerts = restHelper.getCerts("getcertuser", raGetCertRequest, loginResponse.getAccessToken(), httpRequest);
                response.setUser_certificates(ServiceUtils.adaptRaResponseToNeac(responseCerts));
                response.setTransaction_id(request.getTransactionId());
            }else {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(HttpStatus.UNAUTHORIZED, new ValidationErrorResponse("raAuthentication", ValidationError.raAuthentication))
                        .build();
            }
        } catch (Exception e) {
            throw ServiceExceptionBuilder.newBuilder()
                    .addError(HttpStatus.NOT_FOUND, new ValidationErrorResponse("certNotFound", ValidationError.NotFound))
                    .build();
        }
        return response;
    }

    public SignFileResponse signFile(SignFileRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        SignFileResponse response = new SignFileResponse();
        try {
            IdpLoginRequest loginRequest = new IdpLoginRequest(true, "SAP", "");
            RestHelper restHelper = new RestHelper();
            restHelper.init(idpEndpoint, idpKeyStore, idpKeyStorePassword);

            // todo
        } catch (Exception e) {
            throw ServiceExceptionBuilder.newBuilder()
                    .addError(HttpStatus.NOT_FOUND, new ValidationErrorResponse("certNotFound", ValidationError.NotFound))
                    .build();
        }
        return response;
    }

    private List<UserCertificate> adaptRaResponseToNeac (List<RaUserCertificate> responseCerts) {
        List<UserCertificate> listUserCerts = new ArrayList<>();
        for (RaUserCertificate cert : responseCerts) {
            UserCertificate userCertificate = new UserCertificate();
            userCertificate.setCert_id(cert.getCertificateId());
            userCertificate.setCert_data(cert.getBase64Certificate());
            userCertificate.setChain_data(new ChainData());
            userCertificate.setSerial_number(cert.getCertificateSerialNumber());
            listUserCerts.add(userCertificate);
        }
        return listUserCerts;
    }
}
