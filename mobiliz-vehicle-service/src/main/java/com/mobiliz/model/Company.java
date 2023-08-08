package com.mobiliz.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<CompanyFleetGroup> companyFleetGroups = new ArrayList<>();
    @Column(name = "admin_id", nullable = false)
    private Long adminId;
    @Column(name = "admin_name", nullable = false)
    private String adminName;
    @Column(name = "admin_surname", nullable = false)
    private String adminSurname;


    public Company() {
    }

    public Company(String name, Long adminId, String adminName, String adminSurname) {
        this.name = name;
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
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

    public List<CompanyFleetGroup> getCompanyFleetGroups() {
        return companyFleetGroups;
    }

    public void setCompanyFleetGroups(List<CompanyFleetGroup> companyFleetGroups) {
        this.companyFleetGroups = companyFleetGroups;
    }

    public void addCompanyFleetGroup(CompanyFleetGroup companyFleetGroup){
        this.getCompanyFleetGroups().add(companyFleetGroup);
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }

    public void setAdminSurname(String adminSurname) {
        this.adminSurname = adminSurname;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

