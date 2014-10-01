import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

public class Sender {

    private static final HttpClient HTTP_CLIENT = new DefaultHttpClient();

    public HttpResponse sendPost(String url, String payload, Map<String, String> headers) throws Exception {

        HttpPost post = new HttpPost(url);

        for (String key : headers.keySet()) {
            post.setHeader(new BasicHeader(key, headers.get(key)));
        }

        post.setEntity(new StringEntity(payload));

        HttpResponse response = HTTP_CLIENT.execute(post);
        HTTP_CLIENT.getConnectionManager().closeIdleConnections(10, TimeUnit.MILLISECONDS);

        return response;
    }
}
