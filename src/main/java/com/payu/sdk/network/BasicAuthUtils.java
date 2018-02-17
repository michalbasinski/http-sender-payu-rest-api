package com.payu.sdk.network;

import org.apache.commons.codec.binary.Base64;

public class BasicAuthUtils {

    private static final String BASIC_SEPARATOR = ":";
    private static final String BASIC_PREFIX = "Basic ";

    public static String generateAuthorizationHeader(final String login, final String password) {
        StringBuilder builder = new StringBuilder();
        String headerToEncode = builder.append(login).append(BASIC_SEPARATOR).append(password).toString();
        return BASIC_PREFIX + new String(Base64.encodeBase64(headerToEncode.getBytes()));
    }
}
