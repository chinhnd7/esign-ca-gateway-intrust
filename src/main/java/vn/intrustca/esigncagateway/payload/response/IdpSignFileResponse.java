package vn.intrustca.esigncagateway.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdpSignFileResponse {
    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("error_desc")
    private String errorDesc;

    @JsonProperty("request_id")
    private String requestId;
    private List<String> signatures;
}
