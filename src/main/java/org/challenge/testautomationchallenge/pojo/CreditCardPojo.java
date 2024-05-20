package org.challenge.testautomationchallenge.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.challenge.testautomationchallenge.domain.creditCard.CreditCard;

import java.io.File;

public class CreditCardPojo {
    ObjectMapper objectMapper = new ObjectMapper();
    CreditCard creditCard = new CreditCard();

    @SneakyThrows
    public void returnDeserializePayloadCreditCard() {
        creditCard = objectMapper.readValue(new File
                ("src/main/java/org/challenge/testautomationchallenge/data/payload-credit-card.json")
                , CreditCard.class);
    }

    @SneakyThrows
    public String returnSerializePayloadCreditCard() {
        returnDeserializePayloadCreditCard();
        return objectMapper.writeValueAsString(creditCard);
    }

}
