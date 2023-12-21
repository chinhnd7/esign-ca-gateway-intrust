package vn.intrustca.esigncagateway.utils;

import vn.intrustca.esigncagateway.payload.ChainData;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;
import vn.intrustca.esigncagateway.payload.UserCertificate;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {
    private static final String CA_CERT = "MIIAHDFKBTkeKwIBAu";
    private static final String ROOT_CERT = "MIIAAJFNCTuwUoPlat";

    public static List<UserCertificate> adaptRaResponseToNeac (List<RaUserCertificate> responseCerts) {
        List<UserCertificate> listUserCerts = new ArrayList<>();
        for (RaUserCertificate cert : responseCerts) {
            UserCertificate userCertificate = new UserCertificate();
            userCertificate.setCert_id(cert.getCertificateId());
            userCertificate.setCert_data(cert.getBase64Certificate());
            userCertificate.setChain_data(new ChainData(CA_CERT, ROOT_CERT));
            userCertificate.setSerial_number(cert.getCertificateSerialNumber());
            listUserCerts.add(userCertificate);
        }
        return listUserCerts;
    }
}
