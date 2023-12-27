package vn.intrustca.esigncagateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCertificate {
    @JsonProperty("cert_id")
    private int certId;
    @JsonProperty("cert_data")
    private String certData;
    @JsonProperty("chain_data")
    private ChainData chainData;
    @JsonProperty("serial_number")
    private String serialNumber;
}
