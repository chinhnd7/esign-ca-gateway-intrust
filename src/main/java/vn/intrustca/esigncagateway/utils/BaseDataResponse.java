package vn.intrustca.esigncagateway.utils;


import com.fasterxml.jackson.annotation.JsonProperty;
import vn.intrustca.esigncagateway.utils.exception.ValidationErrorResponse;


import java.util.List;

public class BaseDataResponse<T> {
    @JsonProperty("status_code")
    private String responseCode;
    @JsonProperty("message")
    private String responseMessage;
    @JsonProperty("responseEntityMessages")
    private List<ValidationErrorResponse> responseEntityMessages;
    @JsonProperty("data")
    private T data;

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public List<ValidationErrorResponse> getResponseEntityMessages() {
        return this.responseEntityMessages;
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

    @JsonProperty("responseEntityMessages")
    public void setResponseEntityMessages(List<ValidationErrorResponse> responseEntityMessages) {
        this.responseEntityMessages = responseEntityMessages;
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

                Object this$responseEntityMessages = this.getResponseEntityMessages();
                Object other$responseEntityMessages = other.getResponseEntityMessages();
                if (this$responseEntityMessages == null) {
                    if (other$responseEntityMessages != null) {
                        return false;
                    }
                } else if (!this$responseEntityMessages.equals(other$responseEntityMessages)) {
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
        Object $responseEntityMessages = this.getResponseEntityMessages();
        result = result * 59 + ($responseEntityMessages == null ? 43 : $responseEntityMessages.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "BaseDataResponse(responseCode=" + this.getResponseCode() + ", responseMessage=" + this.getResponseMessage() + ", responseEntityMessages=" + this.getResponseEntityMessages() + ", data=" + this.getData() + ")";
    }

    public BaseDataResponse(String responseCode, String responseMessage, List<ValidationErrorResponse> responseEntityMessages, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseEntityMessages = responseEntityMessages;
        this.data = data;
    }

    public BaseDataResponse() {
    }
}
