package vn.intrustca.esigncagateway.utils.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServiceExceptionResponse {
    private int status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private List<ValidationErrorResponse> errors;


    public ServiceExceptionResponse(ServiceException ex) {
        this.status = ex.getStatus().value();
        this.timestamp = ex.getTimestamp();
        this.message = ex.getMessage();
        this.errors = ex.getErrors();
    }
}
