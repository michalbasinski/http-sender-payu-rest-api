package com.payu.sdk.network.senders;

import com.payu.sdk.exceptions.WrongPayloadException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class HttpGetSender extends AbstractHttpSender {
    public PayUHttpResponse sendRequest(String url, String login, String password) throws IOException, WrongPayloadException {

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader(HEADER_AUTHORIZATION, BasicAuthUtils.generateAuthorizationHeader(login, password));
        httpGet.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);

        HttpResponse rawHttpResponse = httpClient.execute(httpGet);
        return new PayUHttpResponse(rawHttpResponse);
    }
}
