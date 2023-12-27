package vn.intrustca.esigncagateway.utils;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;
import vn.intrustca.esigncagateway.payload.response.IdpSignFileResponse;
import vn.intrustca.esigncagateway.utils.exception.BusinessException;
import vn.intrustca.esigncagateway.utils.exception.ExceptionCode;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@PropertySource("classpath:application.properties")
public class RestHelper {
    private RestTemplate restTemplate;
    private String endpoint;

    public void init(String endpoint, String urlKeyStore, String keyStorePassword) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnrecoverableKeyException {
        this.endpoint = endpoint;
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        File file = ResourceUtils.getFile(urlKeyStore);
        clientStore.load(Files.newInputStream(file.toPath()), keyStorePassword.toCharArray());

        SSLContext sslContext = SSLContextBuilder.create()
                .setProtocol("TLSv1.2")
                .loadKeyMaterial(clientStore, keyStorePassword.toCharArray())
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restTemplate = new RestTemplate(requestFactory);
    }

    public <T, G> T callCaService(String apiPath, G request, String authValue, HttpServletRequest httpServletRequest, Class<T> responseClass, String service) {
        try {
            ResponseEntity<T> responseEntity = null;
            if (DefaultValue.RA_SERVICE.equals(service)) {
                responseEntity = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createRaAuthHttpEntity(request, authValue, httpServletRequest), responseClass);
            } else if (DefaultValue.IDP_SERVICE.equals(service)) {
                responseEntity = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createIdpAuthHttpEntity(request, authValue, httpServletRequest), responseClass);
            }
            if (Objects.requireNonNull(responseEntity).getStatusCodeValue() == 200) {
                return responseEntity.getBody();
            } else {
                System.out.println(responseEntity);
                throw new BusinessException(ExceptionCode.LOGIN_FAIL);
            }
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    public <G> List<RaUserCertificate> getCerts(String apiPath, G request, String token, HttpServletRequest httpServletRequest) {
        ResponseEntity<List<RaUserCertificate>> responseEntity;
        try {
            responseEntity = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createRaAuthHttpEntity(request, token, httpServletRequest), new ParameterizedTypeReference<List<RaUserCertificate>>() {
            });
            if (responseEntity.getStatusCodeValue() == 200 && !Objects.requireNonNull(responseEntity.getBody()).isEmpty()) {
                return responseEntity.getBody();
            } else {
                System.out.println(responseEntity);
                throw new BusinessException(ExceptionCode.CERT_NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    public <G> IdpSignFileResponse signFile(String apiPath, G request, String token, HttpServletRequest httpServletRequest) {
        IdpSignFileResponse idpSignFileResponse;
        // Create a Callable (Function Sign)
        Callable<IdpSignFileResponse> functionB = () -> {
            ResponseEntity<IdpSignFileResponse> signFileResponse = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createIdpAuthHttpEntity(request, token, httpServletRequest), IdpSignFileResponse.class);
            return signFileResponse.getBody();
        };
        // Create a FutureTask to hold the result of Function Sign
        FutureTask<IdpSignFileResponse> futureTask = new FutureTask<>(functionB);
        // Start a new thread to execute Function Sign
        new Thread(futureTask).start();
        try {
            // Wait for the result of Function Sign
            idpSignFileResponse = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
        return idpSignFileResponse;
    }

    private HttpEntity createRaAuthHttpEntity(Object requestObject, String tokenValue, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", httpServletRequest.getHeader("User-Agent"));
        if (!StringUtils.isEmpty(tokenValue)) {
            headers.set("Authorization", "Bearer " + tokenValue);
        }
        return new HttpEntity<>(requestObject, headers);
    }

    private HttpEntity createIdpAuthHttpEntity(Object requestObject, String authValue, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", DefaultValue.IDP_USER_AGENT);
        if (authValue.equals(DefaultValue.IDP_NEAC_AUTH_LOGIN)) {
            byte[] encodedAuth = Base64Utils.encode(authValue.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);
        } else {
            headers.set("Authorization", "Bearer " + authValue);
        }
        return new HttpEntity<>(requestObject, headers);
    }
}
