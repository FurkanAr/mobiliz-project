package com.mobiliz.controller.advice;

import com.mobiliz.exception.Permission.UserHasNotPermissionException;
import com.mobiliz.exception.companyGroup.*;
import com.mobiliz.exception.response.ExceptionResponse;
import com.mobiliz.exception.response.ExceptionValidatorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionValidatorResponse> handle(MethodArgumentNotValidException exception, HttpServletRequest request){
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError:: getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity
                .ok(new ExceptionValidatorResponse(
                        "Validation Failed",
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath(),
                        errors));
    }
    @ExceptionHandler(CompanyGroupNotExistException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupNotExistException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(UserHasNotPermissionException.class)
    public ResponseEntity<ExceptionResponse> handle(UserHasNotPermissionException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


    @ExceptionHandler(CompanyGroupIdAndAdminIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupIdAndAdminIdNotMatchedException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


    @ExceptionHandler(CompanyGroupAndCompanyDistrictGroupNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupAndCompanyDistrictGroupNotMatchException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


    @ExceptionHandler(CompanyGroupNameInUseException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupNameInUseException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }
    @ExceptionHandler(CompanyGroupVehiclesInUseException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupVehiclesInUseException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

}
