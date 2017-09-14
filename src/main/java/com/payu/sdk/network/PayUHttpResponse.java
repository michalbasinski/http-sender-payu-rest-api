package com.payu.sdk.network;

public class PayUHttpResponse {
    private String payload;
    private String status;

    public PayUHttpResponse(String payload, String status) {
        this.payload = payload;
        this.status = status;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
