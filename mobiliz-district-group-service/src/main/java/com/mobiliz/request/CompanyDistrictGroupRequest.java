package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyDistrictGroupRequest {

    @NotEmpty(message = "Please enter group name")
    private String name;

    public CompanyDistrictGroupRequest() {
    }

    public CompanyDistrictGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyDistrictGroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
