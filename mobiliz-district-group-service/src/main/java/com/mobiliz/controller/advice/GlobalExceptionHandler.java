package com.mobiliz.controller.advice;


import com.mobiliz.exception.companyDistrictGroup.*;
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

    @ExceptionHandler(CompanyDistrictGroupNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyDistrictGroupNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyDistrictGroupIdAndAdminIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyDistrictGroupIdAndAdminIdNotMatchedException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


    @ExceptionHandler(CompanyDistrictGroupAndCompanyFleetGroupNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyDistrictGroupAndCompanyFleetGroupNotMatchException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


    @ExceptionHandler(CompanyDistrictGroupNameInUseException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyDistrictGroupNameInUseException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyFleetGroupIdAndCompanyIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyFleetGroupIdAndCompanyIdNotMatchedException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(AdminNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyFleetGroupIdAndAdminIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyFleetGroupIdAndAdminIdNotMatchedException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }


}
