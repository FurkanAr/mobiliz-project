package com.mobiliz.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyFleetGroupRequest {

    @NotEmpty(message = "Please enter fleet name")
    private String name;

    public CompanyFleetGroupRequest() {
    }

    public CompanyFleetGroupRequest(String name) {
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
        return "CompanyFleetGroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
