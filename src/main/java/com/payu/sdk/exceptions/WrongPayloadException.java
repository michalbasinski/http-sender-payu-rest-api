package com.payu.sdk.exceptions;

public class WrongPayloadException extends Exception {
    public WrongPayloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
