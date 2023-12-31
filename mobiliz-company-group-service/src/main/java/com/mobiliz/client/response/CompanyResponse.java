package com.mobiliz.client.response;

public class CompanyResponse {

    private Long id;
    private String name;

    public CompanyResponse() {
    }

    public CompanyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
