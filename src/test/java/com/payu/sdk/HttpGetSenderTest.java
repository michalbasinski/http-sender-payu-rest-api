package com.payu.sdk;

import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.messages.converters.ResponseDeserializer;
import com.payu.sdk.messages.converters.ResponseType;
import com.payu.sdk.messages.response.OrderRetrieveResponse;
import com.payu.sdk.network.senders.HttpGetSender;
import com.payu.sdk.network.senders.PayUHttpResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class HttpGetSenderTest {
    @Mock
    private HttpClient mockedHttpClient;

    @InjectMocks
    private HttpGetSender httpGetSender;

    @Before
    public void setUp() throws IOException {
        httpGetSender = new HttpGetSender();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenEncodingWasNotSupported() throws WrongProtocolException, IOException {
        //given
        doThrow(IOException.class).when(mockedHttpClient).execute(any(HttpGet.class));

        try {
            //when
            httpGetSender.sendRequest("URL", "LOGIN", "PASSWORD");
            fail("Exception was not thrown!");
        } catch (Exception e) {
            //thrown
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    public void shouldRetrieveOrderWithoutErrors() throws IOException, WrongPayloadException {
        //given
        byte[] bytes = getClass().getClassLoader().getResourceAsStream("orderRetrieveResponse.json").readAllBytes();
        String orderRetrieveJson = new String(bytes, "UTF-8");
        HttpResponse mockResponse = TestUtils.getMockedHttpClientResponse(orderRetrieveJson, 200);
        doReturn(mockResponse).when(mockedHttpClient).execute(any());

        //when
        PayUHttpResponse httpResponse = httpGetSender.sendRequest("URL", "POS", "PASSWORD");

        //then
        assertEquals("200", httpResponse.getStatus());
        OrderRetrieveResponse orderRetrieveResponse = (OrderRetrieveResponse) ResponseDeserializer.parseResponse(httpResponse.getPayload(), ResponseType.ORDER_RETRIEVE_RESPONSE);
        assertTrue(orderRetrieveResponse.getOrders().size() == 1);
        assertEquals("TLG3NDTQC7180217GUEST000P01",orderRetrieveResponse.getOrders().get(0).getOrderId());
        assertEquals("SUCCESS",orderRetrieveResponse.getStatus().getStatusCode());
    }
}
