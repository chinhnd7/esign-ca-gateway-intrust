package vn.intrustca.esigncagateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCertificate {
    @JsonProperty
    private int cert_id;
    private String cert_data;
    private ChainData chain_data;
    private String serial_number;
}
