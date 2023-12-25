package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCertificate {
    private int cert_id;
    private String cert_data;
    private ChainData chain_data;
    private String serial_number;
}
