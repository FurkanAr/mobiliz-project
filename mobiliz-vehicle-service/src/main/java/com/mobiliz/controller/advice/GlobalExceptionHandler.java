package com.mobiliz.controller.advice;

import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.response.ExceptionResponse;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyGroupNotExistException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupNotExistException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyDistrictGroupNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyDistrictGroupNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyFleetGroupNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyFleetGroupNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(VehicleNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

}
