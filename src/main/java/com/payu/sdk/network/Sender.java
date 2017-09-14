package com.payu.sdk.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
import org.apache.http.message.BasicHeader;

public class Sender {

    private HttpClient httpClient = new DefaultHttpClient();
    private static final int DEFAULT_TIMEOUT = 10;

    public PayUHttpResponse sendPost(String url, String login, String password, String payload) throws WrongPayloadException, WrongProtocolException, HttpClientException, IOException {

        HttpPost post = new HttpPost(url);
        HttpResponse rawHttpResponse;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", BasicAuthUtils.generateAuthorizationHeader(login, password));
        headers.put("Content-type", HttpConstants.CONTENT_TYPE_JSON);

        for (String key : headers.keySet()) {
            post.setHeader(new BasicHeader(key, headers.get(key)));
        }

        try {
            post.setEntity(new StringEntity(payload));
            rawHttpResponse = httpClient.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new WrongPayloadException(e);
        } catch (ClientProtocolException e) {
            throw new WrongProtocolException(e);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } finally {
            httpClient.getConnectionManager().closeIdleConnections(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(rawHttpResponse.getEntity().getContent()));

        StringBuffer responsePayload = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            responsePayload.append(line);
        }

        PayUHttpResponse result = new PayUHttpResponse(String.valueOf(responsePayload), String.valueOf(rawHttpResponse.getStatusLine().getStatusCode()));
        return result;
    }

    public void setHttpClient(HttpClient client) {
        this.httpClient = client;
    }
}
