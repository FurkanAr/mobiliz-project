package com.mobiliz.client.request;

public class AddVehicleRequest {

    private Long userId;
    private String userName;
    private String userSurname;
    private Long companyId;

    public AddVehicleRequest() {
    }

    public AddVehicleRequest(Long userId, String userName, String userSurname, Long companyId) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "AddVehicleRequest{" +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
