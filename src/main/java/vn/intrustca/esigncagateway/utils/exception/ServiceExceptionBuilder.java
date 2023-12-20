package vn.intrustca.esigncagateway.utils.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ServiceExceptionBuilder {
    public ServiceExceptionBuilder() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        public List<ValidationErrorResponse> errors = new ArrayList();
        public HttpStatus status;

        public Builder() {
        }

        public Builder addError(HttpStatus status, ValidationErrorResponse errorResponse) {
            this.errors.add(errorResponse);
            this.status = status;
            return this;
        }

        public ServiceException build() {
            return new ServiceException("failed", this.status, this.errors);
        }
    }
}
