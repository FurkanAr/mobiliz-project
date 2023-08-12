package com.mobiliz.exception.permission;

public class UserHasNotPermissionException extends RuntimeException {
    public UserHasNotPermissionException(String message) {
        super(message);
    }
}
