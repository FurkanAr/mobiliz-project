package com.mobiliz.client.request;

public class TokenRequest {

    private Long userId;

    public TokenRequest() {
    }

    public TokenRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TokenRequest{" +
                "userId=" + userId +
                '}';
    }
}
