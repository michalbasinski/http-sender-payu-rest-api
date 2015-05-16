package com.payu.sdk.exception;

public class WrongProtocolException extends Exception {
    public WrongProtocolException() {
        super();
    }

    public WrongProtocolException(String message) {
        super(message);
    }

    public WrongProtocolException(Exception e) {
        super(e);
    }
}
