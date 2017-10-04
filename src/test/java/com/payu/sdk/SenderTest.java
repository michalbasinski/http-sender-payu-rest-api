package com.payu.sdk;

import com.payu.sdk.exception.HttpClientException;
import com.payu.sdk.network.Sender;
import com.payu.sdk.exception.WrongPayloadException;
import com.payu.sdk.exception.WrongProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class SenderTest extends AbstractTest {

    @Mock
    private HttpClient mockedHttpClient;

    @Mock
    private ClientConnectionManager connectionManager;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenEncodingWasNotSupported() throws WrongProtocolException, WrongPayloadException, HttpClientException, IOException {
        //given
        doThrow(UnsupportedEncodingException.class).when(mockedHttpClient).execute(any(HttpPost.class));
        doReturn(connectionManager).when(mockedHttpClient).getConnectionManager();
        Sender sender = new Sender();
        sender.setHttpClient(mockedHttpClient);

        try {
            //when
            sender.sendPost("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (Exception e) {
            //thrown
            assertTrue(e instanceof WrongPayloadException);
        }
    }

    @Test
    public void shouldThrowExceptionWhenPayloadWasNotSupported() throws WrongProtocolException, WrongPayloadException, HttpClientException, IOException {
        //given
        doThrow(ClientProtocolException.class).when(mockedHttpClient).execute(any(HttpPost.class));
        doReturn(connectionManager).when(mockedHttpClient).getConnectionManager();
        Sender sender = new Sender();
        sender.setHttpClient(mockedHttpClient);

        try {
            //when
            sender.sendPost("URL", "LOGIN", "PASSWORD", "PAYLOAD");
            fail("Exception was not thrown!");
        } catch (Exception e) {
            //thrown
            assertTrue(e instanceof WrongProtocolException);
        }
    }
}
