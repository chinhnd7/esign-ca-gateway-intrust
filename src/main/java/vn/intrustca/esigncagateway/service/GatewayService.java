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
import vn.intrustca.esigncagateway.payload.request.RaGetCertRequest;
import vn.intrustca.esigncagateway.payload.request.GetCertRequest;
import vn.intrustca.esigncagateway.payload.request.RaLoginRequest;
import vn.intrustca.esigncagateway.payload.response.GetCertResponse;
import vn.intrustca.esigncagateway.payload.response.RaLoginResponse;
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
    private String endpoint;

    @Value("${ra.username}")
    private String userName;

    @Value("${ra.password}")
    private String password;

    public GetCertResponse getCerts(GetCertRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        GetCertResponse response = new GetCertResponse();
        try {
            RaLoginRequest loginRequest = new RaLoginRequest(userName, password);
            RestHelper restHelper = new RestHelper();
            restHelper.init(endpoint);

            RaLoginResponse loginResponse = restHelper.callService("auth/signin", loginRequest, null, httpRequest, RaLoginResponse.class);
            if(loginResponse.getCode() == 0){
                RaGetCertRequest raGetCertRequest = new RaGetCertRequest(request.getUserId());
                List<RaUserCertificate> responseCerts = restHelper.getCerts("getcertuser", raGetCertRequest, loginResponse.getAccessToken(), httpRequest);
                response.setUserCertificates(ServiceUtils.adaptRaResponseToNeac(responseCerts));
                response.setTransactionId(request.getTransactionId());
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
