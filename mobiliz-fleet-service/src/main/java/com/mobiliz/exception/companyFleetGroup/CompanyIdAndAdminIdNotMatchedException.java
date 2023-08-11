package com.mobiliz.exception.companyFleetGroup;

public class CompanyIdAndAdminIdNotMatchedException extends RuntimeException {
    public CompanyIdAndAdminIdNotMatchedException(String message) {
        super(message);
    }
}
