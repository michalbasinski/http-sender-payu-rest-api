package com.payu.sdk;

import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.network.senders.HttpGetSender;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
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
}
