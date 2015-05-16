package com.payu.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.payu.sdk.commons.*;
import com.payu.sdk.exception.WrongPayloadException;
import com.payu.sdk.exception.WrongProtocolException;
import com.payu.sdk.requests.OrderCreateRequest;
import com.payu.sdk.requests.builders.OrderCreateRequestBuilder;
import com.payu.sdk.requests.entities.Buyer;
import com.payu.sdk.requests.entities.Product;
import com.payu.sdk.requests.entities.builders.BuyerBuilder;
import com.payu.sdk.requests.entities.builders.ProductBuilder;
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

        List<Product> productList = new ArrayList<Product>();
        productList.add(new ProductBuilder().withName("product1").withUnitPrice(111).withQuantity(1).build());

        Buyer buyer = new BuyerBuilder()
                .withEmail("michal.basinski88@gmail.com")
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("123456789")
                .build();

        OrderCreateRequest orderCreateRequest = new OrderCreateRequestBuilder()
                .withTotalAmount(100).withDescription("description")
                .withAdditionalDescription("additional description")
                .withProducts(productList).withCurrencyCode("PLN")
                .withCustomerIP("46.238.126.146").withMerchantPosId("145227")
                .withBuyer(buyer).withContinueUrl("http://google.pl")
                .build();

        HttpResponse response = sender.sendPost(url, JSONConverter.convertToJSON(orderCreateRequest), headers);
        Map<FieldNames, String> result = httpResponseUtils.parseResponse(response);

        LOGGER.log(Level.INFO, "Sending 'POST' request to URL : " + url +
                               "\nCurrent POS : " + login +
                               "\nHTTP Status: " + result.get(FieldNames.KEY_STATUS) +
                               "\nResponse: " + result.get(FieldNames.KEY_PAYLOAD));
    }
}