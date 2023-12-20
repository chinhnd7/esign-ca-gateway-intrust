package vn.intrustca.esigncagateway.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.UserCertificate;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificateResponse {
    private String transactionId;
    private List<UserCertificate> userCertificates;
}
