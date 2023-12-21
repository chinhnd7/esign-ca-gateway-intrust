package vn.intrustca.esigncagateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RaUserCertificate {
    private int certificateId;
    private String fingerprint;
    private String certificateSerialNumber;
    private String subjectDN;
    private Date notBefore;
    private Date notAfter;
    private Date revocationDate;
    private int revocationReasonId;
    private String base64Certificate;
    private Integer crhId;
}
