package vn.intrustca.esigncagateway.utils.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceExceptionResponse> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new ServiceExceptionResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AppException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(Exception e, HttpServletResponse response) throws IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("responseMessage", "Cert Not Found");
        errorDetails.put("responseCode", 404);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorDetails);
    }
}

