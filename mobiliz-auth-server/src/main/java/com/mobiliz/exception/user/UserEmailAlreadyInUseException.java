package com.mobiliz.exception.user;

public class UserEmailAlreadyInUseException extends RuntimeException {
    public UserEmailAlreadyInUseException(String message) {
        super(message);
    }
}
