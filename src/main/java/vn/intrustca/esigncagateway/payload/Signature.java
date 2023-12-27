package vn.intrustca.esigncagateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Signature {
    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("signature_value")
    private String signatureValue;

    @JsonProperty("timestamp_signature")
    private String timestampSignature;
}
