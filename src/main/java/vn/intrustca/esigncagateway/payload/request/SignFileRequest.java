package vn.intrustca.esigncagateway.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.File;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignFileRequest {
    private String spId;
    private String spPassword;
    private String userId;
    private List<File> listFiles;
    private String transactionId;
    private String serialNumber;
    private String timeStamp;
}
