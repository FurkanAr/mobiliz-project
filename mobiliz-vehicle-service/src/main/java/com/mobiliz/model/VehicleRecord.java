package com.mobiliz.model;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_record")
public class VehicleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_first_surname")
    private String userFirstname;
    @Column(name = "user_surname")
    private String userSurname;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public VehicleRecord() {
    }

    public VehicleRecord(Long userId, String userFirstname, String userSurname) {
        this.userId = userId;
        this.userFirstname = userFirstname;
        this.userSurname = userSurname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "VehicleRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", userFirstname='" + userFirstname + '\'' +
                ", userSurname='" + userSurname + '\'' +
                '}';
    }
}
