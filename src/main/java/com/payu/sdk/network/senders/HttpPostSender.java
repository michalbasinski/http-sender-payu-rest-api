package com.payu.sdk.network.senders;

import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpPostSender extends AbstractHttpSender {

    public PayUHttpResponse sendRequest(String url, String login, String password, String payload) throws WrongPayloadException, WrongProtocolException, IOException {
        try {
            HttpPost post = new HttpPost(url);

            post.setHeader(HEADER_AUTHORIZATION, BasicAuthUtils.generateAuthorizationHeader(login, password));
            post.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);

            post.setEntity(new StringEntity(payload));
            HttpResponse rawHttpResponse = httpClient.execute(post);
            return new PayUHttpResponse(rawHttpResponse);
        } catch (UnsupportedEncodingException e) {
            throw new WrongPayloadException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new WrongProtocolException(e.getMessage(), e);
        }
    }
}
