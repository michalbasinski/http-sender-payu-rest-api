package com.payu.sdk;

import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.messages.converters.ResponseDeserializer;
import com.payu.sdk.messages.converters.ResponseType;
import com.payu.sdk.messages.response.OrderCreateResponse;
import com.payu.sdk.network.senders.HttpPostSender;
import com.payu.sdk.network.senders.PayUHttpResponse;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class HttpPostSenderTest {

    @Mock
    private HttpClient mockedHttpClient;

    @InjectMocks
    private HttpPostSender httpPostSender;

    @Before
    public void setUp() throws IOException {
        httpPostSender = new HttpPostSender();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenEncodingWasNotSupported() throws WrongProtocolException, IOException {
        //given
        doThrow(UnsupportedEncodingException.class).when(mockedHttpClient).execute(any(HttpPost.class));

        try {
            //when
            httpPostSender.sendRequest("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (WrongPayloadException e) {
            //thrown
            assertTrue(e instanceof WrongPayloadException);
        }
    }

    @Test
    public void shouldThrowExceptionWhenPayloadWasNotSupported() throws WrongProtocolException, IOException, WrongPayloadException {
        //given
        doThrow(ClientProtocolException.class).when(mockedHttpClient).execute(any(HttpPost.class));

        try {
            //when
            httpPostSender.sendRequest("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (WrongProtocolException e) {
            //thrown
            assertTrue(e instanceof WrongProtocolException);
        }
    }

    @Test
    public void shouldWrapOrderCreateResponseCorrectly() throws WrongProtocolException, WrongPayloadException, IOException {
        //given
        byte[] orderCreateResponseBytes = getClass().getClassLoader().getResourceAsStream("orderCreateResponse.json").readAllBytes();
        String orderCreateResponseSerialized = new String(orderCreateResponseBytes, "UTF-8");

        HttpResponse responseFromMock = getMockedHttpClientResponse(orderCreateResponseSerialized, 302);
        doReturn(responseFromMock).when(mockedHttpClient).execute(any(HttpPost.class));

        //when
        PayUHttpResponse result = httpPostSender.sendRequest("URL", "LOGIN", "PASSWORD", "PAYLOAD");

        //then
        assertEquals("302", result.getStatus());
        OrderCreateResponse orderCreateResponse = (OrderCreateResponse) ResponseDeserializer.parseResponse(result.getPayload(), ResponseType.ORDER_CREATE_RESPONSE);
        assertEquals("SUCCESS", orderCreateResponse.getStatus().getStatusCode());
        assertEquals("8TLZCH75RX180218GUEST000P01", orderCreateResponse.getOrderId());
        assertTrue(orderCreateResponse.getRedirectUri().contains("https://secure.payu.com/pay/?orderId=8TLZCH75RX180218GUEST000P01&token="));
    }

    private HttpResponse getMockedHttpClientResponse(String responseJson, int httpStatus) throws UnsupportedEncodingException {
        HttpResponse responseFromMock = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("",1,1), httpStatus, "ReasonPhrase"));
        responseFromMock.setEntity(new StringEntity(responseJson));
        return responseFromMock;
    }
}
