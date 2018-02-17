package com.payu.sdk;

import com.payu.sdk.network.senders.BasicAuthUtils;
import junit.framework.TestCase;
import org.junit.Assert;

public class BasicAuthUtilsTest extends TestCase {
    public void testGenerateAuthorizationHeader() throws Exception {
        //given
        String login = "145227";
        String password = "13a980d4f851f3d9a1cfc792fb1f5e50";
        String expectedValue = "Basic MTQ1MjI3OjEzYTk4MGQ0Zjg1MWYzZDlhMWNmYzc5MmZiMWY1ZTUw";

        //when
        String result = BasicAuthUtils.generateAuthorizationHeader(login, password);
        //then
        Assert.assertEquals(expectedValue, result);
    }
}
