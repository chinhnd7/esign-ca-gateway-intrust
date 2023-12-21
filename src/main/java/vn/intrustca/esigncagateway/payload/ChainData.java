package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChainData {
    private String ca_cert;
    private String root_cert;
}
