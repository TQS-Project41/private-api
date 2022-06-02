package com.example.demo.Models;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="address")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "pais é obrigatório")
    private String country;

    @Column
    @NotNull(message = "zipcode é obrigatório")
    private String zipcode;

    @Column
    @NotNull(message = "city é obrigatório")
    private String city;

    @Column
    @NotNull(message = "address é obrigatório")
    private String address;


    @OneToOne(mappedBy = "address")
    private Store store;

    @OneToOne(mappedBy = "address")
    private OrderList orderList;


    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserAddress userAddress;

  



    public Address() {
       
    }



    public Address(String country,
            String zipcode,
             String city,
            String address, Store store, OrderList orderList,
            UserAddress userAddress) {
        this.country = country;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.store = store;
        this.orderList = orderList;
        this.userAddress = userAddress;
    }

    public Address(String country,
    String zipcode,
     String city,
    String address, Store store
            ) {
        this.country = country;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.store = store;

        }

   
        public Address(String country,
        String zipcode,
        String city,
        String address
            ) {
        this.country = country;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;     
        }




    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }



    public String getCountry() {
        return country;
    }



    public void setCountry(String country) {
        this.country = country;
    }



    public String getZipcode() {
        return zipcode;
    }



    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }



    public String getCity() {
        return city;
    }



    public void setCity(String city) {
        this.city = city;
    }



    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
    }



    public Store getStore() {
        return store;
    }



    public void setStore(Store store) {
        this.store = store;
    }



    public OrderList getOrderList() {
        return orderList;
    }



    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }



    public UserAddress getUserAddress() {
        return userAddress;
    }



    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }



    @Override
    public String toString() {
        return "Address [address=" + address + ", city=" + city + ", country=" + country + ", id=" + id + ", orderList="
                + orderList + ", store=" + store + ", userAddress=" + userAddress + ", zipcode=" + zipcode + "]";
    }

    
    
}
