package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ListItemId implements Serializable {
    
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "list_id")
    private Long listId;
    
    public ListItemId() {
    }

    public ListItemId(Long id, Long id2) {
        this.listId = id2;
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }
}
