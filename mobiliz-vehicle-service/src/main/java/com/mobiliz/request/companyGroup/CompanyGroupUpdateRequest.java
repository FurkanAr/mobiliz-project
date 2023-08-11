package com.mobiliz.request.companyGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyGroupUpdateRequest {

    @NotEmpty(message = "Please enter company group name")
    private String name;

    public CompanyGroupUpdateRequest() {
    }

    public CompanyGroupUpdateRequest(String name ) {
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
        return "CompanyGroupUpdateRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
