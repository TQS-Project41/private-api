package com.example.demo.models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Table;

@Table(name="orderList")
@Entity
public class OrderList {
    
    @Id
    @Column(name = "productList_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "productList_id")
    private ProductList productList;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "id" , nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name="store_id", nullable=false)
    private Store store;

    @Column(name = "delivery_id")
    @NotNull(message = "deliveryId é obrigatório")
    private Long deliveryId;

    @Column(name = "delivery_timestamp")
    private LocalDateTime deliveryTimestamp;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime orderTimestamp;

    public OrderList() {
    }

    public OrderList(ProductList productList, Address address, Store store, Long deliveryId, LocalDateTime deliveryTimestamp) {
        this.productList = productList;
        this.address = address;
        this.store = store;
        this.deliveryId = deliveryId;
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public OrderList(ProductList productList, Address address, Store store, Long deliveryId) {
        this(productList, address, store, deliveryId, null);
    }

    public ProductList getProductList() {
        return productList;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Long getDeliveryId() {
        return this.deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public LocalDateTime getDeliveryTimestamp() {
        return this.deliveryTimestamp;
    }

    public void setDeliveryTimestamp(LocalDateTime deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public LocalDateTime getOrderTimestamp() {
        return this.orderTimestamp;
    }
    
}
