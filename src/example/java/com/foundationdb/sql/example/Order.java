package com.foundationdb.sql.example;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name="ORDERS")
@Table(appliesTo="ORDERS")
@AccessType("property")
public class Order {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @javax.persistence.Column(nullable = false )
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getOrder_info() {
        return order_info;
    }
    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }
    
    @javax.persistence.Temporal(TemporalType.DATE)
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn( nullable = false )
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Order() {
        
    }

    private long id;
    private String order_info;
    private Date order_date;
    private Customer customer;

}
