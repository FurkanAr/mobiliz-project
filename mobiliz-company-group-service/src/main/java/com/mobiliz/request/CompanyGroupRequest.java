package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;

public class CompanyGroupRequest {

    @NotEmpty(message = "Please enter company group name")
    private String name;

    public CompanyGroupRequest() {
    }

    public CompanyGroupRequest(String name) {
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
        return "CompanyGroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
