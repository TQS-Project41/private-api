package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

import javax.persistence.Table;

@Table(name="productListItem")
@Entity
public class ProductListItem {
   
    @EmbeddedId
    private ListItemId id;

    @Column
    @NotNull(message = "amount é obrigatório")
    private int amount;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(name="list_id", nullable=false)
    private ProductList list;

    
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    public ProductListItem() {
    }
    
    
    public ProductListItem(int amount, ProductList list, Product product) {
        this.id = new ListItemId(product.getId(), list.getId());
        this.amount = amount;
        this.list = list;
        this.product = product;
    }


    @Override
    public String toString() {
        return "ProductListItem [amount=" + amount + ", product=" + product + "]";
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public ListItemId getId() {
        return id;
    }


    public void setId(ListItemId id) {
        this.id = id;
    }


    public ProductList getList() {
        return list;
    }


    public void setList(ProductList list) {
        this.list = list;
    }

    
}
