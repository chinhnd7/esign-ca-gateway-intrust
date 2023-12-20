package vn.intrustca.esigncagateway.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RaLoginResponse {
    private int code;
    private String accessToken;
    private String type;
    private String message;
    private List<String> role;
}

