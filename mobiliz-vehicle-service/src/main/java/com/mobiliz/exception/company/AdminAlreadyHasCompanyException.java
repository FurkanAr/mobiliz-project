package com.mobiliz.exception.company;

public class AdminAlreadyHasCompanyException extends RuntimeException {
    public AdminAlreadyHasCompanyException(String message) {
        super(message);
    }
}
