package com.springbatch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CsvModel {

    @Id
    @GeneratedValue
    private Long id;

    private String surName;

    private String firstName;

    private String init;

    private String addrL1;

    private String addrL2;

    private String city;

    private String province;

    private String country;

    private int zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getAddrL1() {
        return addrL1;
    }

    public void setAddrL1(String addrL1) {
        this.addrL1 = addrL1;
    }

    public String getAddrL2() {
        return addrL2;
    }

    public void setAddrL2(String addrL2) {
        this.addrL2 = addrL2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

}
