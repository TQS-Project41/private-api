package com.example.demo.Models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class ProductListItemId implements Serializable {
    
    

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "list_id")
    private Long listId;
    
    
    public ProductListItemId() {
    }


    public ProductListItemId(Long id, long id2) {
        this.listId=id2;
        this.productId=id;
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((listId == null) ? 0 : listId.hashCode());
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductListItemId other = (ProductListItemId) obj;
        if (listId == null) {
            if (other.listId != null)
                return false;
        } else if (!listId.equals(other.listId))
            return false;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        return true;
    }
    
}
