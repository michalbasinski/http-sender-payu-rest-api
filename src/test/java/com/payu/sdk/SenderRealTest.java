package com.payu.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.payu.sdk.exception.HttpClientException;
import com.payu.sdk.exception.WrongPayloadException;
import com.payu.sdk.exception.WrongProtocolException;
import com.payu.sdk.messages.converters.JSONConverter;
import com.payu.sdk.messages.converters.ResponseType;
import com.payu.sdk.messages.entities.Buyer;
import com.payu.sdk.messages.entities.Product;
import com.payu.sdk.messages.entities.builders.BuyerBuilder;
import com.payu.sdk.messages.entities.builders.ProductBuilder;
import com.payu.sdk.messages.request.OpenPayURequest;
import com.payu.sdk.messages.request.OrderCreateRequest;
import com.payu.sdk.messages.request.builders.OrderCreateRequestBuilder;
import com.payu.sdk.messages.response.OrderCreateResponse;
import com.payu.sdk.network.PayUHttpResponse;
import com.payu.sdk.network.Sender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SenderRealTest extends AbstractTest {

    private Logger LOGGER = Logger.getGlobal();
    private String url;
    private String login;
    private String password;

    @Before
    public void setUp() throws IOException {
        prepareEnvironmentProperties();
        url = properties.getProperty("productionUrlAPIv2_1");
        login = properties.getProperty("productionPos");
        password = properties.getProperty("productionSecondKeyMD5");
    }

    @Test
    @Ignore
    public void shouldCreateNewOrderWithoutErrors() throws WrongProtocolException, WrongPayloadException, HttpClientException, IOException {
        Sender sender = new Sender();

        OpenPayURequest orderCreateRequest = prepareOrderCreateRequest();

        PayUHttpResponse result = sender.sendPost(url, login, password, JSONConverter.convertToJSON(orderCreateRequest));

        OrderCreateResponse orderCreateResponse = (OrderCreateResponse) JSONConverter.parseResponse(result.getPayload(), ResponseType.ORDER_CREATE_RESPONSE);

        LOGGER.log(Level.INFO, "Sending 'POST' request to URL : " + url +
                               "\nCurrent POS : " + login +
                               "\nHTTP Status: " + result.getStatus() +
                               "\nResponse: " + result.getPayload());

        Assert.assertTrue(orderCreateResponse.getOrderId() != null);
        Assert.assertTrue(orderCreateResponse.getRedirectUri() != null);
        Assert.assertEquals("SUCCESS", orderCreateResponse.getStatus().getStatusCode());
    }

    private OrderCreateRequest prepareOrderCreateRequest() {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new ProductBuilder().withName("product1").withUnitPrice(111).withQuantity(1).build());

        Buyer buyer = new BuyerBuilder()
                .withEmail("michal.basinski88@gmail.com")
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("123456789")
                .build();

        return new OrderCreateRequestBuilder()
                .withTotalAmount(100).withDescription("description")
                .withAdditionalDescription("additional description")
                .withProducts(productList).withCurrencyCode("PLN")
                .withCustomerIP("46.238.126.146").withMerchantPosId("145227")
                .withBuyer(buyer).withContinueUrl("http://google.pl")
                .build();
    }
}