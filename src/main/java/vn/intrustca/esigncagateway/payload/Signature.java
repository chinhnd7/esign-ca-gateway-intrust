package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Signature {
    private String docId;
    private String signatureValue;
    private String timestampSignature;
}
