package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {
    private String dataToBeSigned;
    private String docId;
    private String fileType;
    private String signType;
}
