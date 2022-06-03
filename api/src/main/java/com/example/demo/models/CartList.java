package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="cartList")
@Entity
public class CartList {
    @Id
    @Column(name = "productList_id")
    private Long id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "productList_id")
    private ProductList productList;


    public CartList() {
    }


    public CartList(ProductList productList) {
        this.productList = productList;
    }


    public ProductList getProductList() {
        return productList;
    }


    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    
}
