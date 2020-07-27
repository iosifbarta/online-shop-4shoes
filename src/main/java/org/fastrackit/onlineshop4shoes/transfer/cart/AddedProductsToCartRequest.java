package org.fastrackit.onlineshop4shoes.transfer.cart;

import java.util.List;

public class AddedProductsToCartRequest {

    private long userId;
    private List<Long> productsIds;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getProductsIds() {
        return productsIds;
    }

    public void setProductsIds(List<Long> productsIds) {
        this.productsIds = productsIds;
    }

    @Override
    public String toString() {
        return "AddedProductsToCartRequest{" +
                "userId=" + userId +
                ", productsIds=" + productsIds +
                '}';
    }
}
