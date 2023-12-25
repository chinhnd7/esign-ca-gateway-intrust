package vn.intrustca.esigncagateway.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.Signature;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignFileResponse {
    private String transaction_id;
    private List<Signature> signatures;
}
