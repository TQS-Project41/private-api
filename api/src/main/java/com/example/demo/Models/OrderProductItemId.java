package com.example.demo.Models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class OrderProductItemId implements Serializable {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "orderList_id")
    private Long orderListId;

    public OrderProductItemId() {
    }

    public OrderProductItemId(Long productId, Long orderListId) {
        this.productId = productId;
        this.orderListId = orderListId;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(Long orderListId) {
        this.orderListId = orderListId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderListId == null) ? 0 : orderListId.hashCode());
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
        OrderProductItemId other = (OrderProductItemId) obj;
        if (orderListId == null) {
            if (other.orderListId != null)
                return false;
        } else if (!orderListId.equals(other.orderListId))
            return false;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        return true;
    }

    
}
