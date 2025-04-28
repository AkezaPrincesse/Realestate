package com.innova.realestate.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String district;
    private String sector;
    private String streetNo;
    private String city;

    public Address() {
    }
    public Address(String district, String sector, String streetNo, String city) {
        this.district = district;
        this.sector = sector;
        this.streetNo = streetNo;
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
