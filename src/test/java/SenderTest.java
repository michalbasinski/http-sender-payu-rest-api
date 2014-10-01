import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

public class SenderTest extends AbstractTest {
    private static final String CONTENT_TYPE_JSON = "application/json";
    private Map<String, String> headers;
    private HttpResponseUtils httpResponseUtils;
    @Before
    public void setUp() throws IOException {
        prepareEnvironmentProperties();
        headers = new HashMap<String, String>();
        httpResponseUtils = new HttpResponseUtils();
    }

    @Test
    public void senderTestPayerProvison() throws Exception {
        Sender sender = new Sender();
        String url = properties.getProperty("productionUrlAPIv2_1");
        String login = properties.getProperty("productionPos");
        String password = properties.getProperty("productionSecondKeyMD5");

        headers.put("Authorization", BasicAuthUtils.generateAuthorizationHeader(login, password));
        headers.put("Content-type", CONTENT_TYPE_JSON);

        String payload = "{" +
                " \"cancelUrl\":\"http://google.pl\"," +
                " \"completeUrl\":\"http://google.pl\"," +
                " \"customerIp\":\"46.238.126.146\"," +
                " \"merchantPosId\":\"145227\"," +
                " \"validityTime\":1200000," +
                " \"description\":\"Sklep On-line: płatność na kwotę 400,00\"," +
                " \"additionalDescription\":\"654321\"," +
                " \"currencyCode\":\"PLN\"," +
                " \"totalAmount\":40000," +
                " \"buyer\":{" +
                "       \"customerId\":\"123\"," +
                "       \"email\":\"michal.basinski88@gmail.com\"," +
                "       \"phone\":\"666666999\"," +
                "       \"firstName\":\"John\"," +
                "       \"lastName\":\"D\"}," +
                "\"products\":[{\"name\":\"super hiper iPhone\",\"unitPrice\":40000,\"quantity\":1}]}";

        HttpResponse response = sender.sendPost(url, payload, headers);
        Map<FieldNames, String> result = httpResponseUtils.parseResponse(response);

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Current POS : " + login);
        System.out.println("Status : " + result.get(FieldNames.KEY_STATUS));
        System.out.println("Payload : " + result.get(FieldNames.KEY_PAYLOAD));
    }
}