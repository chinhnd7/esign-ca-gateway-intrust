package vn.intrustca.esigncagateway.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCertRequest {
    @JsonProperty("sp_id")
    private String spId;

    @JsonProperty("sp_password")
    private String spPassword;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("serial_number")
    private String serialNumber;
}
