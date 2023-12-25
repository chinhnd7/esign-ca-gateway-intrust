package vn.intrustca.esigncagateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.intrustca.esigncagateway.payload.request.GetCertRequest;
import vn.intrustca.esigncagateway.payload.request.SignFileRequest;
import vn.intrustca.esigncagateway.payload.response.GetCertResponse;
import vn.intrustca.esigncagateway.payload.response.SignFileResponse;
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

    @PostMapping("/get_cert")
    public ResponseEntity<BaseDataResponse<?>> getCerts(@Valid @RequestBody GetCertRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        GetCertResponse response = gatewayService.getCerts(request, httpRequest);
        return ResponseUtil.wrap(response);
    }

    @PostMapping("/sign_file")
    public ResponseEntity<BaseDataResponse<?>> signFile(@Valid @RequestBody SignFileRequest request, HttpServletRequest httpRequest) throws JsonProcessingException, ServiceException {
        SignFileResponse response = gatewayService.signFile(request, httpRequest);
        return ResponseUtil.wrap(response);
    }
}
