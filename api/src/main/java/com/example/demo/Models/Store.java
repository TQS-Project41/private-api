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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="store")
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "name é obrigatório")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id" , nullable = false)
    private Address address;


    @OneToMany(mappedBy="store")
    private Set<OrderList> orderList;
    
    
    public Store() {
        this.orderList= new HashSet<>();
    }


    public Store(@NotNull(message = "name é obrigatório") String name, Address address, Set<OrderList> orderList) {
        this.name = name;
        this.address = address;
        this.orderList = orderList;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }


    public Set<OrderList> getOrderList() {
        return orderList;
    }


    public void setOrderList(Set<OrderList> orderList) {
        this.orderList = orderList;
    }


    @Override
    public String toString() {
        return "Store [address=" + address + ", id=" + id + ", name=" + name + ", orderList=" + orderList + "]";
    }
    
    
    

}
