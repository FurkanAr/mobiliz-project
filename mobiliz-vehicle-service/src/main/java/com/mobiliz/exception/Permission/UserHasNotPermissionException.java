package com.mobiliz.exception.Permission;

public class UserHasNotPermissionException extends RuntimeException {
    public UserHasNotPermissionException(String message) {
        super(message);
    }
}
