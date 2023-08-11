package com.mobiliz.request;

import javax.validation.constraints.NotEmpty;

public class CompanyFleetUpdateRequest {

    @NotEmpty(message = "Please enter fleet name")
    private String name;

    public CompanyFleetUpdateRequest() {
    }

    public CompanyFleetUpdateRequest(String name) {
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
        return "CompanyFleetUpdateRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
