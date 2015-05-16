package com.payu.sdk.requests.builders;

import com.payu.sdk.requests.OrderCreateRequest;
import com.payu.sdk.requests.entities.Buyer;
import com.payu.sdk.requests.entities.Product;

import java.util.List;

public class OrderCreateRequestBuilder {
    private OrderCreateRequest orderCreateRequest = new OrderCreateRequest();

    public OrderCreateRequestBuilder withTotalAmount(long totalAmount) {
        orderCreateRequest.setTotalAmount(totalAmount);
        return this;
    }

    public OrderCreateRequestBuilder withContinueUrl(String continueUrl) {
        orderCreateRequest.setContinueUrl(continueUrl);
        return this;
    }


    public OrderCreateRequestBuilder withCustomerIP(String customerIP) {
        orderCreateRequest.setCustomerIP(customerIP);
        return this;
    }

    public OrderCreateRequestBuilder withCurrencyCode(String currencyCode) {
        orderCreateRequest.setCurrencyCode(currencyCode);
        return this;
    }

    public OrderCreateRequestBuilder withDescription(String description) {
        orderCreateRequest.setDescription(description);
        return this;
    }

    public OrderCreateRequestBuilder withMerchantPosId(String merchantPosId) {
        orderCreateRequest.setMerchantPosId(merchantPosId);
        return this;
    }

    public OrderCreateRequestBuilder withAdditionalDescription(String additionalDescription) {
        orderCreateRequest.setAdditionalDescription(additionalDescription);
        return this;
    }

    public OrderCreateRequestBuilder withProducts(List<Product> products) {
        orderCreateRequest.setProducts(products);
        return this;
    }

    public OrderCreateRequestBuilder withBuyer(Buyer buyer) {
        orderCreateRequest.setBuyer(buyer);
        return this;
    }

    public OrderCreateRequestBuilder withNotifyUrl(String notifyUrl) {
        orderCreateRequest.setNotifyUrl(notifyUrl);
        return this;
    }

    public OrderCreateRequest build() {
        return orderCreateRequest;
    }
}
