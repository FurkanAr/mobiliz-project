package com.mobiliz.controller.advice;

import com.mobiliz.exception.Permission.UserHasNotPermissionException;
import com.mobiliz.exception.company.AdminAlreadyHasCompanyException;
import com.mobiliz.exception.company.AdminNotFoundException;
import com.mobiliz.exception.company.CompanyNameInUseException;
import com.mobiliz.exception.company.CompanyNotFoundException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupAndCompanyFleetGroupNotMatchException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyDistrictGroup.CompanyDistrictGroupNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNameInUseException;
import com.mobiliz.exception.companyFleetGroup.CompanyFleetGroupNotFoundException;
import com.mobiliz.exception.companyFleetGroup.CompanyIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyGroup.CompanyGroupAndCompanyDistrictGroupNotMatchException;
import com.mobiliz.exception.companyGroup.CompanyGroupIdAndAdminIdNotMatchedException;
import com.mobiliz.exception.companyGroup.CompanyGroupNotExistException;
import com.mobiliz.exception.response.ExceptionResponse;
import com.mobiliz.exception.response.ExceptionValidatorResponse;
import com.mobiliz.exception.vehicle.VehicleNotFoundException;
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

    @ExceptionHandler(UserHasNotPermissionException.class)
    public ResponseEntity<ExceptionResponse> handle(UserHasNotPermissionException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(AdminAlreadyHasCompanyException.class)
    public ResponseEntity<ExceptionResponse> handle(AdminAlreadyHasCompanyException exception, HttpServletRequest request) {
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

    @ExceptionHandler(CompanyIdAndAdminIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyIdAndAdminIdNotMatchedException exception, HttpServletRequest request) {
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
    @ExceptionHandler(CompanyNameInUseException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyNameInUseException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

    @ExceptionHandler(CompanyFleetGroupNameInUseException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyFleetGroupNameInUseException exception, HttpServletRequest request) {
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

    @ExceptionHandler(CompanyGroupIdAndAdminIdNotMatchedException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupIdAndAdminIdNotMatchedException exception, HttpServletRequest request) {
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

    @ExceptionHandler(CompanyGroupAndCompanyDistrictGroupNotMatchException.class)
    public ResponseEntity<ExceptionResponse> handle(CompanyGroupAndCompanyDistrictGroupNotMatchException exception, HttpServletRequest request) {
        return ResponseEntity
                .ok(new ExceptionResponse(exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getServletPath()));
    }

}
