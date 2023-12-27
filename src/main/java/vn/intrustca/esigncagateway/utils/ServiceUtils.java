package vn.intrustca.esigncagateway.utils;

import vn.intrustca.esigncagateway.payload.ChainData;
import vn.intrustca.esigncagateway.payload.RaUserCertificate;
import vn.intrustca.esigncagateway.payload.Signature;
import vn.intrustca.esigncagateway.payload.UserCertificate;
import vn.intrustca.esigncagateway.payload.request.SignFileRequest;
import vn.intrustca.esigncagateway.payload.response.IdpSignFileResponse;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtils {

    public static List<UserCertificate> adaptRaCertResponseToNeac (List<RaUserCertificate> responseCerts) {
        List<UserCertificate> listUserCerts = new ArrayList<>();
        for (RaUserCertificate cert : responseCerts) {
            UserCertificate userCertificate = new UserCertificate();
            userCertificate.setCertId(cert.getCertificateId());
            userCertificate.setCertData(cert.getBase64Certificate());
            userCertificate.setChainData(new ChainData(DefaultValue.CA_CERT, DefaultValue.ROOT_CERT));
            userCertificate.setSerialNumber(cert.getCertificateSerialNumber());
            listUserCerts.add(userCertificate);
        }
        return listUserCerts;
    }

    public static List<Signature> adaptIdpSignatureResponseToNeac (SignFileRequest request, IdpSignFileResponse responseSignatures) {
        List<Signature> listSignatures = new ArrayList<>();
        for (int i = 0; i < responseSignatures.getSignatures().size(); i++) {
            Signature signature = new Signature();
            signature.setSignatureValue(responseSignatures.getSignatures().get(i));
            signature.setDocId(request.getListFiles().get(i).getDocId());
            signature.setTimestampSignature(request.getTimeStamp());
            listSignatures.add(signature);
        }
        return listSignatures;
    }
}
