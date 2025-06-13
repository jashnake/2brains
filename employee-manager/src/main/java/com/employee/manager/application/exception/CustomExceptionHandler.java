package com.employee.manager.application.exception;

import com.employee.manager.application.constants.ApplicationConstants;
import com.employee.manager.application.util.LogUtil;
import com.employee.manager.application.util.ManageErrorsUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Enumeration;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ManageErrorsUtil manageErrorsUtil;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LogUtil.printLogError(ex.getMessage(), status);
        return manageErrorsUtil.createErrorMessage(ApplicationConstants.ERROR_INVALID_PARAMS, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LogUtil.printLogError(ex.getMessage(), status);
        return manageErrorsUtil.createErrorMessage(ApplicationConstants.ERROR_INVALID_HEADERS, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        LogUtil.printLogError(ex.getMessage(), status);
        return manageErrorsUtil.createErrorMessage(ApplicationConstants.ERROR_INVALID_HEADERS, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LogUtil.printLogError(ex.getMessage(), status);
        return manageErrorsUtil.createErrorMessage(ApplicationConstants.ERROR_INVALID_HEADERS, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException (ConstraintViolationException ex, WebRequest request,
                                                                         HttpServletRequest httpRequest) {
        var headers = getHeaders(httpRequest);
        LogUtil.printLogError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return manageErrorsUtil.createErrorMessage(ApplicationConstants.ERROR_INVALID_PARAMS, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmployeeException.class})
    public ResponseEntity<ErrorMessage> businessExceptionHandler(EmployeeException exception, HttpServletRequest httpRequest){
        var headers = getHeaders(httpRequest);
        int httpCode = exception.getErrorMessage().getHttpCode();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(httpCode == 400) {
            status = HttpStatus.BAD_REQUEST;
            LogUtil.printLogError(exception.getErrorMessage().getReason(), HttpStatus.BAD_REQUEST);
        }else if(httpCode == 503){
            status = HttpStatus.SERVICE_UNAVAILABLE;
            LogUtil.printLogError(exception.getErrorMessage().getReason(), HttpStatus.SERVICE_UNAVAILABLE);
        }else if(httpCode == 404){
            status = HttpStatus.NOT_FOUND;
            LogUtil.printLogError(exception.getErrorMessage().getReason(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(exception.getErrorMessage(),status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> generalExceptionHandler(Exception exception,WebRequest request, HttpServletRequest httpRequest){
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var headers = getHeaders(httpRequest);
        LogUtil.printLogError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(manageErrorsUtil.getError500(exception.getMessage()) , httpStatus);
    }

    public HttpHeaders getHeaders(HttpServletRequest httpRequest){
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, httpRequest.getHeader(headerName));
        }
        return headers;
    }

}