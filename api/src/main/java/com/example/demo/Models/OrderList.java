package com.example.demo.Models;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @OneToMany(mappedBy="orderList")
    private Set<OrderProductItem> orderProductItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id" , nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name="store_id", nullable=false)
    private Store store;

    

    @Column(name = "delivery_id")
    @NotNull(message = "deliveryId é obrigatório")
    private Long deliveryId;

    @Column(name = "delivery_timestamp")
    @NotNull(message = "deliveryTimestamp é obrigatório")
    private Long deliveryTimestamp;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTimestamp;

    public OrderList() {
        this.orderProductItem= new HashSet<>();
    }

    public OrderList(ProductList productList, Set<OrderProductItem> productListItems) {
        this.productList = productList;
        this.orderProductItem = productListItems;
        this.orderProductItem= new HashSet<>();
    }

    

    public OrderList(ProductList productList, Set<OrderProductItem> orderProductItem, Address address, Store store,
             Long deliveryId,
            Long deliveryTimestamp) {
        this.productList = productList;
        this.orderProductItem = orderProductItem;
        this.address = address;
        this.store = store;
        this.deliveryId = deliveryId;
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public ProductList getProductList() {
        return productList;
    }
    
    public Set<OrderProductItem> getOrderProductItem() {
        return orderProductItem;
    }

    public void setOrderProductItem(Set<OrderProductItem> orderProductItem) {
        this.orderProductItem = orderProductItem;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    
    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public void setDeliveryTimestamp(Long deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }

    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    public Set<OrderProductItem> getProductListItems() {
        return orderProductItem;
    }

    public void setProductListItems(Set<OrderProductItem> productListItems) {
        this.orderProductItem = productListItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
