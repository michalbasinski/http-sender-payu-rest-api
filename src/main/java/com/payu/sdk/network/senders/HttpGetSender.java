package com.payu.sdk.network.senders;

import com.payu.sdk.exceptions.WrongPayloadException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class HttpGetSender extends AbstractHttpSender {
    public PayUHttpResponse sendRequest(String url, String login, String password) throws IOException, WrongPayloadException {

        HttpGet post = new HttpGet(url);

        post.setHeader(HEADER_AUTHORIZATION, BasicAuthUtils.generateAuthorizationHeader(login, password));
        post.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);

        HttpResponse rawHttpResponse = httpClient.execute(post);
        return new PayUHttpResponse(rawHttpResponse);
    }
}
