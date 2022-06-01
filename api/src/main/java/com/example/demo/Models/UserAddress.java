package com.example.demo.Models;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="userAddress")
@Entity
public class UserAddress {
    @EmbeddedId
    private UserAddressID id;


    @OneToOne
    @MapsId("userId")
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    
    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name="address_id", nullable=false)
    private Address address;


    public UserAddress(User user, Address address) {
        this.id= new UserAddressID(user.getId(),address.getId());
        this.user = user;
        this.address = address;
    }


    public UserAddress() {
    }


    public UserAddressID getId() {
        return id;
    }


    public void setId(UserAddressID id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "UserAddress [address=" + address + ", id=" + id + ", user=" + user + "]";
    }

    
}
