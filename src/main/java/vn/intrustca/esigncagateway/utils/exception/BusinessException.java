package vn.intrustca.esigncagateway.utils.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
