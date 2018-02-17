package com.payu.sdk.network;

import com.payu.sdk.exceptions.HttpClientException;
import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Sender {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private HttpClient httpClient = new DefaultHttpClient();

    public PayUHttpResponse sendPost(String url, String login, String password, String payload) throws WrongPayloadException, WrongProtocolException, HttpClientException, IOException {

        HttpPost post = new HttpPost(url);
        post.setHeader(HEADER_AUTHORIZATION, BasicAuthUtils.generateAuthorizationHeader(login, password));
        post.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);

        HttpResponse rawHttpResponse;
        try {
            post.setEntity(new StringEntity(payload));
            rawHttpResponse = httpClient.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new WrongPayloadException(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            throw new WrongProtocolException(e.getMessage(), e);
        } catch (IOException e) {
            throw new HttpClientException(e.getMessage(), e);
        }
        return new PayUHttpResponse(rawHttpResponse);
    }
}
