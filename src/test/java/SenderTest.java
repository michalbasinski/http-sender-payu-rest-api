import exception.WrongPayloadException;
import exception.WrongProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.googlecode.catchexception.CatchException.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

public class SenderTest extends AbstractTest {

    @Mock
    private HttpClient mockedHttpClient;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenEncodingWasNotSupported() throws IOException, WrongProtocolException, WrongPayloadException {
        //given
        doThrow(UnsupportedEncodingException.class).when(mockedHttpClient).execute(any(HttpPost.class));
        Sender sender = new Sender();
        sender.setHttpClient(mockedHttpClient);

        //when
        catchException(sender).sendPost("URL", "LOGIN", new HashMap<String, String>());

        //then
        assertTrue(caughtException() instanceof WrongPayloadException);
    }

    @Test
    public void shouldThrowExceptionWhenPayloadWasNotSupported() throws IOException, WrongProtocolException, WrongPayloadException {
        //given
        doThrow(ClientProtocolException.class).when(mockedHttpClient).execute(any(HttpPost.class));
        Sender sender = new Sender();
        sender.setHttpClient(mockedHttpClient);

        //when
        catchException(sender).sendPost("URL", "LOGIN", new HashMap<String, String>());

        //then
        assertTrue(caughtException() instanceof WrongProtocolException);
    }
}
