package com.foundationdb.sql.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name="ADDRESSES")
@Table(appliesTo="ADDRESSES")
@AccessType("property")
public class Address {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Column(name="address_info", length=200)
    public String getAddressInfo() {
        return addressInfo;
    }
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }
    @Column(name="city", length=20)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    @Column(name="state", length=2)
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Address() {
    }

    private long id;
    private String addressInfo;
    private String city;
    private String state;
    
}
