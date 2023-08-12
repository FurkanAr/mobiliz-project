package com.mobiliz.exception.companyGroup;

public class CompanyGroupNotExistException extends RuntimeException {

    public CompanyGroupNotExistException(String message){
        super(message);
    }
}
