package com.payu.sdk.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import com.payu.sdk.authentication.BasicAuthUtils;
import com.payu.sdk.exception.HttpClientException;
import com.payu.sdk.exception.WrongPayloadException;
import com.payu.sdk.exception.WrongProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Sender {

    private HttpClient httpClient = new DefaultHttpClient();
    private static final int DEFAULT_TIMEOUT = 10;

    public PayUHttpResponse sendPost(String url, String login, String password, String payload) throws WrongPayloadException, WrongProtocolException, HttpClientException, IOException {

        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", BasicAuthUtils.generateAuthorizationHeader(login, password));
        post.setHeader("Content-type", HttpConstants.CONTENT_TYPE_JSON);

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
        } finally {
            httpClient.getConnectionManager().closeIdleConnections(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        }

        return new PayUHttpResponse(rawHttpResponse);
    }

    public void setHttpClient(HttpClient client) {
        this.httpClient = client;
    }
}
