package vn.intrustca.esigncagateway.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCertRequest {
    private String sp_id;
    private String sp_password;
    private String user_id;
    private String transaction_id;
    private String serial_number;
}
