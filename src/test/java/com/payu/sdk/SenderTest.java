package com.payu.sdk;

import com.payu.sdk.exceptions.HttpClientException;
import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.network.Sender;
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

public class SenderTest {

    @Mock
    private HttpClient mockedHttpClient;

    @InjectMocks
    private Sender sender;

    @Before
    public void setUp() throws IOException {
        sender = new Sender();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenEncodingWasNotSupported() throws WrongProtocolException, HttpClientException, IOException {
        //given
        doThrow(UnsupportedEncodingException.class).when(mockedHttpClient).execute(any(HttpPost.class));

        try {
            //when
            sender.sendPost("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (WrongPayloadException e) {
            //thrown
            assertTrue(e instanceof WrongPayloadException);
        }
    }

    @Test
    public void shouldThrowExceptionWhenPayloadWasNotSupported() throws WrongProtocolException, HttpClientException, IOException, WrongPayloadException {
        //given
        doThrow(ClientProtocolException.class).when(mockedHttpClient).execute(any(HttpPost.class));

        try {
            //when
            sender.sendPost("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (WrongProtocolException e) {
            //thrown
            assertTrue(e instanceof WrongProtocolException);
        }
    }
}
