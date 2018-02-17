package com.payu.sdk;

import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.network.senders.HttpPostSender;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
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
}
