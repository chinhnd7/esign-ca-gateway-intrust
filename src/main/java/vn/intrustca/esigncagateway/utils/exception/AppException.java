package vn.intrustca.esigncagateway.utils.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private int responseCode;
    private String responseMessage;

    public AppException(String responseMessage)
    {
        super();
        this.responseMessage = responseMessage;
    }
    public AppException(int responseCode, String responseMessage)
    {
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}
