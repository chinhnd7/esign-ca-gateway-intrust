package vn.intrustca.esigncagateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChainData {
    @JsonProperty("ca_cert")
    private String caCert;
    @JsonProperty("root_cert")
    private String rootCert;
}
