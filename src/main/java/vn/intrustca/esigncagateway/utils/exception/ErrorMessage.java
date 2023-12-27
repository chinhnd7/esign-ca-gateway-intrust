package vn.intrustca.esigncagateway.utils.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage<T> {
    @JsonProperty("status_code")
    private String statusCode;
    private String message;
}
