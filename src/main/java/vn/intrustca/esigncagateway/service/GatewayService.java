package vn.intrustca.esigncagateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.intrustca.esigncagateway.payload.request.GetCertRequest;
import vn.intrustca.esigncagateway.payload.request.RaLoginRequest;
import vn.intrustca.esigncagateway.payload.response.CertificateResponse;
import vn.intrustca.esigncagateway.payload.response.RaLoginResponse;
import vn.intrustca.esigncagateway.utils.RestHelper;
import vn.intrustca.esigncagateway.utils.exception.ServiceException;
import vn.intrustca.esigncagateway.utils.exception.ServiceExceptionBuilder;
import vn.intrustca.esigncagateway.utils.exception.ValidationError;
import vn.intrustca.esigncagateway.utils.exception.ValidationErrorResponse;

import javax.servlet.http.HttpServletRequest;

@Service
public class GatewayService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Value("${ra.endpoint}")
    private String endpoint;

    @Value("${ra.username}")
    private String userName;

    @Value("${ra.password}")
    private String password;

    public CertificateResponse getCert(GetCertRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        CertificateResponse response = new CertificateResponse();
        try {
            RaLoginRequest loginRequest = new RaLoginRequest(userName, password);
            RestHelper restHelper = new RestHelper();
            restHelper.init(endpoint);

            RaLoginResponse loginResponse = restHelper.callService("auth/signin", loginRequest, null, httpRequest, RaLoginResponse.class);

            if(loginResponse.getCode() == 0){
                response = restHelper.getCert("get_cert", request, loginResponse.getAccessToken(), httpRequest, CertificateResponse.class);

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
}
