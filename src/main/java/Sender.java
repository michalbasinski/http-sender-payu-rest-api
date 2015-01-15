import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import exception.WrongPayloadException;
import exception.WrongProtocolException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

public class Sender {

    private static final HttpClient HTTP_CLIENT = new DefaultHttpClient();
    private static final int DEFAULT_TIMEOUT = 10;

    public HttpResponse sendPost(String url, String payload, Map<String, String> headers) throws WrongPayloadException, WrongProtocolException, IOException {

        HttpPost post = new HttpPost(url);
        HttpResponse response;

        for (String key : headers.keySet()) {
            post.setHeader(new BasicHeader(key, headers.get(key)));
        }

        try {
            post.setEntity(new StringEntity(payload));
            response = HTTP_CLIENT.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new WrongPayloadException(e);
        } catch (ClientProtocolException e) {
            throw new WrongProtocolException(e);
        }

        HTTP_CLIENT.getConnectionManager().closeIdleConnections(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);

        return response;
    }
}
