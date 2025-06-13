package com.employee.manager.application.util;

import com.employee.manager.application.exception.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ManageErrorsUtil {

    public ResponseEntity<Object> createErrorMessage(String msg, HttpHeaders headers, HttpStatusCode status){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setHttpCode(status.value());
        errorMessage.setReason(msg.toUpperCase());
        return new ResponseEntity<>(errorMessage, headers, status);
    }

    public ErrorMessage getError400(String error) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setHttpCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setReason(error);
        return errorMessage;
    }

    public ErrorMessage getError404(String error) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setHttpCode(HttpStatus.NOT_FOUND.value());
        errorMessage.setReason(error);
        return errorMessage;
    }

    public ErrorMessage getError500(String error) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setReason(error);
        return errorMessage;
    }

}
