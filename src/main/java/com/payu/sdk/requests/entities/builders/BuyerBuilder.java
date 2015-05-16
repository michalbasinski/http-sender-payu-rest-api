package com.payu.sdk.requests.entities.builders;

import com.payu.sdk.requests.entities.Buyer;

public class BuyerBuilder {
    private Buyer buyer = new Buyer();

    public BuyerBuilder withEmail(String email) {
        buyer.setEmail(email);
        return this;
    }

    public BuyerBuilder withFirstName(String firstName) {
        buyer.setFirstName(firstName);
        return this;
    }

    public BuyerBuilder withLastName(String lastName) {
        buyer.setLastName(lastName);
        return this;
    }

    public BuyerBuilder withPhone(String phone) {
        buyer.setPhone(phone);
        return this;
    }

    public Buyer build() {
        return buyer;
    }
}
