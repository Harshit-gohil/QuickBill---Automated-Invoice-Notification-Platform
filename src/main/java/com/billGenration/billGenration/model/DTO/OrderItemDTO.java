package com.billGenration.billGenration.model.DTO;

import com.billGenration.billGenration.model.orderitem;

import java.util.List;

public class OrderItemDTO {

    private Long productId;
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
