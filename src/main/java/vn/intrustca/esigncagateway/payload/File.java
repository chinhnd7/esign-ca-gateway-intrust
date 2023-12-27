package vn.intrustca.esigncagateway.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class File {
    @JsonProperty("data_to_be_signed")
    private String dataToBeSigned;

    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("sign_type")
    private String signType;
}
