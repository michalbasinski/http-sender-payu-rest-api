package com.payu.sdk.network.senders;

import com.payu.sdk.exceptions.WrongPayloadException;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PayUHttpResponse {
    private String payload;
    private String status;

    public PayUHttpResponse(HttpResponse rawHttpResponse) throws WrongPayloadException {
        BufferedReader rd;
        StringBuffer responsePayload = new StringBuffer();
        try {
            rd = new BufferedReader(new InputStreamReader(rawHttpResponse.getEntity().getContent()));
            String line;
            while ((line = rd.readLine()) != null) {
                responsePayload.append(line);
            }
        } catch (IOException e) {
            throw new WrongPayloadException("Http response to PayU format parsing failed", e);
        }
        this.payload = String.valueOf(responsePayload);
        this.status = String.valueOf(rawHttpResponse.getStatusLine().getStatusCode());
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
