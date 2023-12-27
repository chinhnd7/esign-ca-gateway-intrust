package vn.intrustca.esigncagateway.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.intrustca.esigncagateway.payload.UserCertificate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetCertResponse {
    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("user_certificates")
    private List<UserCertificate> userCertificates;
}
