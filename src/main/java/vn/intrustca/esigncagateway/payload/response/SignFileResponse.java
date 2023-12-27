package vn.intrustca.esigncagateway.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.Signature;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignFileResponse {
    @JsonProperty("transaction_id")
    private String transactionId;
    private List<Signature> signatures;
}
