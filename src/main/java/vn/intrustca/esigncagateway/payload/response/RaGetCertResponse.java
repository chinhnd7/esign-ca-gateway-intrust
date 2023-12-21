package vn.intrustca.esigncagateway.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RaGetCertResponse {
    private List<RaUserCertificate> raUserCertificates;
}
