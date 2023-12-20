package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCertificate {
    private String certId;
    private String certData;
    private ChainData chainData;
    private String serialNumber;
}
