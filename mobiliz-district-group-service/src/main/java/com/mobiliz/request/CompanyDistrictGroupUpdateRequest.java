package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;

public class CompanyDistrictGroupUpdateRequest {

    @NotEmpty(message = "Please enter group name")
    private String name;

    public CompanyDistrictGroupUpdateRequest() {
    }

    public CompanyDistrictGroupUpdateRequest(String name) {
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
        return "CompanyDistrictGroupUpdateRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
