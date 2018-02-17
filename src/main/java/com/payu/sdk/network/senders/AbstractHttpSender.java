package com.payu.sdk.network.senders;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class AbstractHttpSender {
    protected static final String HEADER_AUTHORIZATION = "Authorization";
    protected static final String HEADER_CONTENT_TYPE = "Content-type";
    protected static final String CONTENT_TYPE_JSON = "application/json";

    protected HttpClient httpClient = new DefaultHttpClient();
}
