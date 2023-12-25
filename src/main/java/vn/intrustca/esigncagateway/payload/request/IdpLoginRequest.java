package vn.intrustca.esigncagateway.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class IdpLoginRequest {
    private boolean rememberMe;
    private String auth_type;
    private String device_id;
}
