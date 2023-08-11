package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyUpdateRequest {

    @NotEmpty(message = "Please enter company name")
    private String name;


    public CompanyUpdateRequest() {
    }

    public CompanyUpdateRequest(String name) {
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
        return "CompanyUpdateRequest{" +
                ", name='" + name + '\'' +
                '}';
    }
}
