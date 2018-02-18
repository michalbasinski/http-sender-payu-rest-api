package com.payu.sdk;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.UnsupportedEncodingException;

public class TestUtils {
    public static HttpResponse getMockedHttpClientResponse(String responseJson, int httpStatus) throws UnsupportedEncodingException {
        HttpResponse responseFromMock =
                new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("", 1, 1), httpStatus, "ReasonPhrase"));
        responseFromMock.setEntity(new StringEntity(responseJson));
        return responseFromMock;
    }
}
