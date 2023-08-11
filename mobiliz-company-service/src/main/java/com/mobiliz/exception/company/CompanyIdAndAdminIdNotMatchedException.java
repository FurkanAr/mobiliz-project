package com.mobiliz.exception.company;

public class CompanyIdAndAdminIdNotMatchedException extends RuntimeException {
    public CompanyIdAndAdminIdNotMatchedException(String message) {
        super(message);
    }
}
