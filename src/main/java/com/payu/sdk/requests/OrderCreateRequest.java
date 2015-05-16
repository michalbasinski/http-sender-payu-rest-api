package com.payu.sdk.requests;

import com.payu.sdk.requests.entities.Buyer;
import com.payu.sdk.requests.entities.Product;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class OrderCreateRequest {
    private long totalAmount;
    private String continueUrl;
    private String merchantPosId;
    private String description;
    private String additionalDescription;
    private String currencyCode;
    private String customerIp;
    private String notifyUrl;
    private List<Product> products;
    private Buyer buyer;

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getContinueUrl() {
        return continueUrl;
    }

    public void setContinueUrl(String continueUrl) {
        this.continueUrl = continueUrl;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public void setMerchantPosId(String merchantPosId) {
        this.merchantPosId = merchantPosId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIP(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
