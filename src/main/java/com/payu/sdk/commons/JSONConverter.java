package com.payu.sdk.commons;

import com.payu.sdk.requests.OrderCreateRequest;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JSONConverter {

    public static String convertToJSON(OrderCreateRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }
}
