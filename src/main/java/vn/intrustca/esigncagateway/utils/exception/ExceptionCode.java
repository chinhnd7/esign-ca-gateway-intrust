package vn.intrustca.esigncagateway.utils.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    SUCCESS("200", "success"),
    INVALID_DOCUMENT("400", "invalid.document"),
    INVALID_CREDENTIALS("401", "invalid.credentials"),
    INVALID_FORMAT_HASHCODE("402", "invalid.format.hashcode"),
    INVALID_CERT("403", "invalid.cert"),
    CERT_NOT_FOUND("404", "cert.not.found"),
    LOGIN_FAIL("405", "login.fail"),
    INTERNAL_SERVER_ERROR("500", "internal-server-error"),
    SIGN_FAIL("501", "sign.failed");

    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}