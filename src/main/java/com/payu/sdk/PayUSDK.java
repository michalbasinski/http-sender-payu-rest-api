package com.payu.sdk;

import com.payu.sdk.messages.response.OpenPayUResponse;

public interface PayUSDK {
    OpenPayUResponse createOrder();
    OpenPayUResponse getOrder();
    OpenPayUResponse updateOrder();
    OpenPayUResponse deleteOrder();
}
