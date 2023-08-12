package com.mobiliz.client.request;

public class UserCompanyGroupSaveRequest {

    private Long userId;
    private String userFirstName;
    private String userSurName;

    public UserCompanyGroupSaveRequest() {
    }

    public UserCompanyGroupSaveRequest(Long userId, String userFirstName, String userSurName) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userSurName = userSurName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserSurName() {
        return userSurName;
    }

    public void setUserSurName(String userSurName) {
        this.userSurName = userSurName;
    }

    @Override
    public String toString() {
        return "UserCompanyGroupSaveRequest{" +
                "userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userSurName='" + userSurName + '\'' +
                '}';
    }
}
