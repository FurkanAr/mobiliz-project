package com.mobiliz.exception.company;

public class CompanyNameInUseException extends RuntimeException {
    public CompanyNameInUseException(String message) {
        super(message);
    }
}
