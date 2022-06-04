package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="orderProductItem")
@Entity
public class OrderProductItem {
    
    @EmbeddedId
    private OrderProductItemId id;
    
    @Column
    @NotNull(message = "price é obrigatório")
    private double price;

    @ManyToOne
    @MapsId("orderListId")
    @JoinColumn(name="orderList_id", nullable=false)
    private OrderList orderList;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    
    
    public OrderProductItem() {
    }
    
    public OrderProductItem( double price,
            OrderList orderList, Product product) {
        this.id = new OrderProductItemId(product.getId(),orderList.getId());
        this.price = price;
        this.orderList = orderList;
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderProductItem [id=" + id + ", orderList=" + orderList + ", price=" + price + ", product=" + product
                + "]";
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderProductItemId getId() {
        return id;
    }

    public void setId(OrderProductItemId id) {
        this.id = id;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    
}
