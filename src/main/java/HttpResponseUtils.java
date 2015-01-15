import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;

public class HttpResponseUtils {

    public Map<FieldNames, String> parseResponse(HttpResponse httpResponse) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

        StringBuffer responsePayload = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            responsePayload.append(line);
        }

        Map<FieldNames, String> result = new HashMap<FieldNames, String>();
        result.put(FieldNames.KEY_PAYLOAD, responsePayload.toString());
        result.put(FieldNames.KEY_STATUS, String.valueOf(httpResponse.getStatusLine().getStatusCode()));

        return result;
    }
}
