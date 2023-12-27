package vn.intrustca.esigncagateway.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdpSignFileRequest {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("serial_number")
    private String serialNumber;
    private int numSignatures;
    private List<String> hash;
    private String keyPass;
}
