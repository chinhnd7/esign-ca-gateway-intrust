package vn.intrustca.esigncagateway.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdpLoginResponse {
    @JsonProperty("status_code")
    private int code;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("DeviceStatus")
    private String deviceStatus;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("expires_in")
    private int expiresIn;
}
