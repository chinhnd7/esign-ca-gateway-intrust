package vn.intrustca.esigncagateway.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.intrustca.esigncagateway.payload.UserCertificate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetCertResponse {
    private String transactionId;

    private List<UserCertificate> userCertificates;
}
