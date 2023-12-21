package vn.intrustca.esigncagateway.utils;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import vn.intrustca.esigncagateway.utils.exception.AppException;
import vn.intrustca.esigncagateway.utils.exception.ValidationError;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@PropertySource("classpath:application.properties")
public class RestHelper {

    @Autowired
    private RestTemplate restTemplate;
    private String endpoint;

    public void init(String endpoint) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnrecoverableKeyException {
        this.endpoint = endpoint;
        String trustStorePassword = "bO4YFtgvruxv";
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        clientStore.load(new FileInputStream("C:\\Intrust\\backup\\esign-ca-gateway\\src\\main\\resources\\client.keystore.p12"), trustStorePassword.toCharArray());

        SSLContext sslContext = SSLContextBuilder.create()
                .setProtocol("TLSv1.2")
                .loadKeyMaterial(clientStore, trustStorePassword.toCharArray())
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restTemplate =  new RestTemplate(requestFactory);
    }

    public <T, G> T callService(String apiPath, G request, String token, HttpServletRequest httpServletRequest,  Class<T> responseClass) {
        try{
            ResponseEntity<T> responseEntity = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createAuthHttpEntity(request, token, httpServletRequest), responseClass);
            if (responseEntity.getStatusCodeValue() == 200) {
                T response = responseEntity.getBody();
                return response;
            }else {
                System.out.println(responseEntity);
            }
        }catch (HttpClientErrorException e){
            throw new AppException(e.getRawStatusCode(), e.getMessage());
        }
        return null;
    }

    public <G> JSONObject getCerts(String apiPath, G request, String token, HttpServletRequest httpServletRequest, Class<JSONObject> jsonObjectClass) {
        ResponseEntity<JSONObject> responseEntity;
        try{
            responseEntity = this.restTemplate.exchange(this.endpoint + apiPath, HttpMethod.POST, this.createAuthHttpEntity(request, token, httpServletRequest), jsonObjectClass);
            if (responseEntity.getStatusCodeValue() == 200) {
                JSONObject response = responseEntity.getBody();
                System.out.println("-------------------------" + response);
                return response;
            }else {
                System.out.println(responseEntity);
            }
        }catch (HttpClientErrorException e){
            throw new AppException(e.getRawStatusCode(), e.getMessage());
        }

        throw new AppException(responseEntity.getStatusCodeValue(), ValidationError.NotFound);
    }

    private HttpEntity createAuthHttpEntity(Object requestObject, String tokenValue, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", httpServletRequest.getHeader("User-Agent"));
        if (!StringUtils.isEmpty(tokenValue)) {
            headers.set("Authorization", "Bearer " + tokenValue);
        }
        return new HttpEntity<>(requestObject, headers);
    }
}
