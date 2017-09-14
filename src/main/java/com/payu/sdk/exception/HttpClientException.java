package com.payu.sdk.exception;

import java.io.IOException;

public class HttpClientException extends Throwable {
    public HttpClientException(IOException e) {super(e);}
    public HttpClientException(String message) {super(message);}
}
