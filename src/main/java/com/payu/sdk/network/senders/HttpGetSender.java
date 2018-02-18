package com.payu.sdk.network.senders;

import com.payu.sdk.exceptions.PayuSdkException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class HttpGetSender extends AbstractHttpSender {
    public PayUHttpResponse sendRequest(String url, String login, String password) throws PayuSdkException {

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader(HEADER_AUTHORIZATION, BasicAuthUtils.generateAuthorizationHeader(login, password));
        httpGet.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);

        HttpResponse rawHttpResponse = null;
        try {
            rawHttpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new PayuSdkException(e.getMessage(), e);
        }
        return new PayUHttpResponse(rawHttpResponse);
    }
}
