package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import javax.persistence.Table;

@Table(name="userAddress")
@Entity
public class UserAddress {
    @Id
    @Column(name = "address_id", nullable = false)
    private long addressId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;  

    public UserAddress(User user, Address address) {
        this.user = user;
        this.address = address;
    }

    public UserAddress() {
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
