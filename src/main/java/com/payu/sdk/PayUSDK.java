package com.payu.sdk;

import com.payu.sdk.exceptions.PayUException;
import com.payu.sdk.messages.request.OrderCreateRequest;
import com.payu.sdk.messages.request.OrderStatusUpdateRequest;
import com.payu.sdk.messages.request.RefundCreateRequest;
import com.payu.sdk.messages.response.OpenPayUResponse;
import com.payu.sdk.messages.response.OrderCreateResponse;
import com.payu.sdk.messages.response.OrderRetrieveResponse;
import com.payu.sdk.messages.response.RefundCreateResponse;

public interface PayUSDK {
    OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest) throws PayUException;
    OrderRetrieveResponse retrieveOrder(String orderId) throws PayUException;
    OpenPayUResponse updateOrder(OrderStatusUpdateRequest orderStatusUpdateRequest);
    OpenPayUResponse cancelOrder(String orderId);

    RefundCreateResponse createRefund(RefundCreateRequest refundCreateRequest);
}
