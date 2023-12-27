package vn.intrustca.esigncagateway.utils.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.intrustca.esigncagateway.utils.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceExceptionResponse> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new ServiceExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({ AppException.class })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleNotFoundException(Exception e, HttpServletResponse response) throws IOException {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("responseMessage", "Cert Not Found");
//        errorDetails.put("responseCode", 404);
//        response.setStatus(HttpStatus.NOT_FOUND.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getWriter(), errorDetails);
//    }
    @ExceptionHandler({AppException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(AppException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getResponseMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        log.error("error occurred.", ex);
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .statusCode(ex.getExceptionCode().getCode())
                .message(MessageUtil.getMessage(ex.getExceptionCode().getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    //Lỗi định dạng input
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorMessage> handleValidationException(ValidationException ex) {
        log.error("error occurred.", ex);
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .statusCode(ExceptionCode.INVALID_DOCUMENT.getCode())
                .message(getValidationMessage(ex))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    private String getValidationMessage(ValidationException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            return fieldErrors.get(0).getDefaultMessage();
        }
        return null;
    }
    //Lỗi hệ thống
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        log.error("error occurred.", ex);
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .statusCode(ExceptionCode.INTERNAL_SERVER_ERROR.getCode())
                .message(MessageUtil.getMessage(ExceptionCode.INTERNAL_SERVER_ERROR.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}

