package vn.intrustca.esigncagateway.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.Signature;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignatureResponse {
    private String transactionId;
    private List<Signature> signatures;
}
