package com.payu.sdk;

import com.payu.sdk.exceptions.PayUException;
import com.payu.sdk.exceptions.WrongPayloadException;
import com.payu.sdk.exceptions.WrongProtocolException;
import com.payu.sdk.messages.converters.RequestSerializer;
import com.payu.sdk.messages.converters.ResponseDeserializer;
import com.payu.sdk.messages.converters.ResponseType;
import com.payu.sdk.messages.entities.Buyer;
import com.payu.sdk.messages.entities.Product;
import com.payu.sdk.messages.entities.builders.BuyerBuilder;
import com.payu.sdk.messages.entities.builders.ProductBuilder;
import com.payu.sdk.messages.request.OpenPayURequest;
import com.payu.sdk.messages.request.OrderCreateRequest;
import com.payu.sdk.messages.request.builders.OrderCreateRequestBuilder;
import com.payu.sdk.messages.response.OrderCreateResponse;
import com.payu.sdk.messages.response.OrderRetrieveResponse;
import com.payu.sdk.network.senders.HttpGetSender;
import com.payu.sdk.network.senders.HttpPostSender;
import com.payu.sdk.network.senders.PayUHttpResponse;
import com.payu.sdk.properties.PropertyNames;
import com.payu.sdk.properties.SDKProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All tests are ignored to avoid sending requests to PayU with every maven build
 */
public class PayUIntegrationTest {

    private Logger LOGGER = Logger.getGlobal();
    private String orderCreateUrl;
    private String orderRetrieveUrl;

    private String login;
    private String password;

    private HttpPostSender httpPostSender;

    private HttpGetSender httpGetSender;

    @Before
    public void setUp() throws IOException {
        orderCreateUrl = SDKProperties.getProperty(PropertyNames.URL);
        orderRetrieveUrl = SDKProperties.getProperty(PropertyNames.ORDER_RETRIEVE_URL);
        login = SDKProperties.getProperty(PropertyNames.POS);
        password = SDKProperties.getProperty(PropertyNames.SECOND_KEY_MD5);
        httpPostSender = new HttpPostSender();
        httpGetSender = new HttpGetSender();
    }

    @Test
    @Ignore
    public void shouldCreateNewOrderWithoutErrors() throws WrongProtocolException, WrongPayloadException, IOException {
        OpenPayURequest orderCreateRequest = prepareOrderCreateRequest();

        PayUHttpResponse result = httpPostSender.sendRequest(orderCreateUrl, login, password, RequestSerializer.serializeRequest(orderCreateRequest));

        OrderCreateResponse orderCreateResponse = (OrderCreateResponse) ResponseDeserializer.parseResponse(result.getPayload(), ResponseType.ORDER_CREATE_RESPONSE);

        LOGGER.log(Level.INFO, "Sending 'POST' request to URL : " + orderCreateUrl +
                "\nCurrent POS : " + login +
                "\nHTTP Status: " + result.getStatus() +
                "\nResponse: " + result.getPayload());

        Assert.assertTrue(orderCreateResponse.getOrderId() != null);
        Assert.assertTrue(orderCreateResponse.getRedirectUri() != null);
        Assert.assertEquals("SUCCESS", orderCreateResponse.getStatus().getStatusCode());
    }

    @Test
    @Ignore
    public void testRetrieve() throws IOException, WrongPayloadException {
        String[] args = new String[]{"TLG3NDTQC7180217GUEST000P01"};
        MessageFormat format = new MessageFormat(orderRetrieveUrl);
        String url = format.format(args);
        PayUHttpResponse result = httpGetSender.sendRequest(url, login, password);
        OrderRetrieveResponse orderRetrieveResponse = (OrderRetrieveResponse) ResponseDeserializer.parseResponse(result.getPayload(), ResponseType.ORDER_RETRIEVE_RESPONSE);
        System.out.println(result.getPayload());
    }

    @Test
    @Ignore
    public void testSDKRetrieve() throws PayUException {
        String orderId = "TLG3NDTQC7180217GUEST000P01";
        PayUSDK sdk = new PayUSDKImpl();
        OrderRetrieveResponse retrieveResponse = (OrderRetrieveResponse) sdk.retrieveOrder(orderId);
        System.out.println(retrieveResponse);
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