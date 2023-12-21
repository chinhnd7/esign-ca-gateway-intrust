package vn.intrustca.esigncagateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.intrustca.esigncagateway.payload.request.GetCertRequest;
import vn.intrustca.esigncagateway.payload.response.GetCertResponse;
import vn.intrustca.esigncagateway.service.GatewayService;
import vn.intrustca.esigncagateway.utils.BaseDataResponse;
import vn.intrustca.esigncagateway.utils.ResponseUtil;
import vn.intrustca.esigncagateway.utils.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/gateway/ca")
public class GatewayController {
    @Autowired
    private GatewayService gatewayService;

//    @PostMapping("/get_cert")
//    public ResponseEntity<ApiResponse<CertificateListData>> getCert(@RequestBody GetCertRequest request, HttpServletRequest httpRequest) {
//        CertificateListData certificateListData = gatewayService.getCert(httpRequest);
//        ApiResponse<CertificateListData> response = new ApiResponse<>();
//        response.setStatusCode(200);
//        response.setMessage("Success");
//        response.setData(certificateListData);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PostMapping("/sign_file")
//    public ResponseEntity<ApiResponse<SignatureListData>> signFile(@RequestBody SignFileRequest request) {
//        SignatureListData signatureListData = gatewayService.signFile();
//        ApiResponse<SignatureListData> response = new ApiResponse<>();
//        response.setStatusCode(200);
//        response.setMessage("Success");
//        response.setData(signatureListData);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping("/get_cert")
    public ResponseEntity<BaseDataResponse<?>> getCerts(@Valid @RequestBody GetCertRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        GetCertResponse response = gatewayService.getCerts(request, httpRequest);
        return ResponseUtil.wrap(response);
    }
}
