package com.payu.sdk.requests.entities.builders;

import com.payu.sdk.requests.entities.Product;

public class ProductBuilder {
    private Product product = new Product();

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withUnitPrice(long unitPrice) {
        product.setUnitPrice(unitPrice);
        return this;
    }

    public ProductBuilder withQuantity(long quantity) {
        product.setQuantity(quantity);
        return this;
    }

    public Product build() {
        return product;
    }
}
