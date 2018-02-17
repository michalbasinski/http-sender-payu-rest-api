package com.payu.sdk.properties;

public enum PropertyNames {
    URL("url"),
    ORDER_RETRIEVE_URL("orderRetrieveUrl"),
    TRANSACTION_RETRIEVE_URL("transactionRetrieveUrl"),
    ORDER_UPDATE_URL("orderUpdateUrl"),
    POS("pos"),
    SECOND_KEY_MD5("secondKeyMD5");

    private String propertyName;

    PropertyNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
