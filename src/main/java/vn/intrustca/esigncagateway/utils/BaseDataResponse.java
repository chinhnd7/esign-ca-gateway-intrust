package vn.intrustca.esigncagateway.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseDataResponse<T> {
    @JsonProperty("status_code")
    private String responseCode;
    @JsonProperty("message")
    private String responseMessage;

    @JsonProperty("data")
    private T data;

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public T getData() {
        return this.data;
    }

    @JsonProperty("status_code")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty("message")
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @JsonProperty("data")
    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseDataResponse)) {
            return false;
        } else {
            BaseDataResponse<?> other = (BaseDataResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$responseCode = this.getResponseCode();
                    Object other$responseCode = other.getResponseCode();
                    if (this$responseCode == null) {
                        if (other$responseCode == null) {
                            break label59;
                        }
                    } else if (this$responseCode.equals(other$responseCode)) {
                        break label59;
                    }

                    return false;
                }

                Object this$responseMessage = this.getResponseMessage();
                Object other$responseMessage = other.getResponseMessage();
                if (this$responseMessage == null) {
                    if (other$responseMessage != null) {
                        return false;
                    }
                } else if (!this$responseMessage.equals(other$responseMessage)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseDataResponse;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $responseCode = this.getResponseCode();
        result = result * 59 + ($responseCode == null ? 43 : $responseCode.hashCode());
        Object $responseMessage = this.getResponseMessage();
        result = result * 59 + ($responseMessage == null ? 43 : $responseMessage.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "BaseDataResponse(responseCode=" + this.getResponseCode() + ", responseMessage=" + this.getResponseMessage() + ", data=" + this.getData() + ")";
    }

    public BaseDataResponse(String responseCode, String responseMessage, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public BaseDataResponse() {
    }
}
