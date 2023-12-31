package com.mobiliz.request;

import com.mobiliz.model.UserRole;
import com.mobiliz.validator.UserRoleSubset;

import javax.validation.constraints.*;
import java.util.Set;

public class UserRequest {

    @NotEmpty(message = "Please enter your username")
    @Size(max = 15, min = 5, message = "Username: Invalid username, Username size should be between 5 to 15")
    private String userName;
    @NotEmpty(message = "Please enter your name")
    private String name;
    @NotEmpty(message = "Please enter your surname")
    private String surName;
    @NotNull(message = "Please enter your companyId")
    private Long companyId;
    @NotEmpty(message = "Please enter your companyName")
    private String companyName;
    @NotNull(message = "Please enter your fleetId")
    private Long fleetId;
    @NotEmpty(message = "Please enter your company fleet name")
    private String companyFleetName;
    @NotEmpty(message = "Please enter your Email")
    @Email(message = "Invalid Email. Please enter proper Email")
    private String email;
    @NotEmpty(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$", message = "Password: " +
            "At least one upper case English letter," +
            "At least one lower case English letter," +
            "At least one digit," +
            "At least one special character," +
            "Min 8 characters," +
            "Max 20 characters")
    private String password;
    @NotEmpty(message = "Please enter your role.")
    @UserRoleSubset(enumClass = UserRole.class)
    private String role;

    public UserRequest() {
    }

    public UserRequest(String userName, String name, String surName, Long companyId,
                       String companyName, Long fleetId, String companyFleetName,
                       String email, String password, String role) {
        this.userName = userName;
        this.name = name;
        this.surName = surName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.fleetId = fleetId;
        this.companyFleetName = companyFleetName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getFleetId() {
        return fleetId;
    }

    public void setFleetId(Long fleetId) {
        this.fleetId = fleetId;
    }

    public String getCompanyFleetName() {
        return companyFleetName;
    }

    public void setCompanyFleetName(String companyFleetName) {
        this.companyFleetName = companyFleetName;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
