import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import commons.Constants;
import exception.WrongPayloadException;
import exception.WrongProtocolException;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

public class SenderRealTest extends AbstractTest {

    private Map<String, String> headers;
    private HttpResponseUtils httpResponseUtils;

    private Logger LOGGER = Logger.getGlobal();

    @Before
    public void setUp() throws IOException {
        prepareEnvironmentProperties();
        headers = new HashMap<String, String>();
        httpResponseUtils = new HttpResponseUtils();
    }

    @Test
    public void shouldCreateNewOrderWithoutErrors() throws WrongProtocolException, WrongPayloadException, IOException {
        Sender sender = new Sender();
        String url = properties.getProperty("productionUrlAPIv2_1");
        String login = properties.getProperty("productionPos");
        String password = properties.getProperty("productionSecondKeyMD5");

        headers.put("Authorization", BasicAuthUtils.generateAuthorizationHeader(login, password));
        headers.put("Content-type", Constants.CONTENT_TYPE_JSON);

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
                "\"products\":[" +
                "{  \"name\":\"super hiper iPhone\"," +
                "   \"unitPrice\":40000," +
                "   \"quantity\":1}" +
                "]" +
                "}";

        HttpResponse response = sender.sendPost(url, payload, headers);
        Map<FieldNames, String> result = httpResponseUtils.parseResponse(response);

        LOGGER.log(Level.INFO, "Sending 'POST' request to URL : " + url +
                               "\nCurrent POS : " + login +
                               "\nHTTP Status: " + result.get(FieldNames.KEY_STATUS) +
                               "\nResponse: " + result.get(FieldNames.KEY_PAYLOAD));
    }
}