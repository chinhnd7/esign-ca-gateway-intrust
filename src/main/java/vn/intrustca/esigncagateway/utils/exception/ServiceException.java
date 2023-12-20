package vn.intrustca.esigncagateway.utils.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceException extends Throwable {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private List<ValidationErrorResponse> errors;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, HttpStatus status, List<ValidationErrorResponse> errors) {
        super(message);
        this.errors = errors;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, List<ValidationErrorResponse> errors, Throwable cause) {
        super(message, cause);
        this.errors = errors;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(List<ValidationErrorResponse> errors, Throwable cause) {
        super(cause);
        this.errors = errors;
    }

    public List<ValidationErrorResponse> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ValidationErrorResponse> errors) {
        this.errors = errors;
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
