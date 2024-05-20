package org.challenge.testautomationchallenge.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.challenge.testautomationchallenge.domain.accounts.Account;
import org.challenge.testautomationchallenge.domain.consents.Consent;

import java.io.File;

public class ConsentPojo {
    ObjectMapper objectMapper = new ObjectMapper();
    Consent consent = new Consent();

    @SneakyThrows
    public void deserializePayloadConsent() {
        consent = objectMapper.readValue(new File
                ("src/main/java/org/challenge/testautomationchallenge/data/payload-consents.json"), Consent.class);
    }

    @SneakyThrows
    public String returnSerializePayloadConsent() {
        deserializePayloadConsent();
        return objectMapper.writeValueAsString(consent);
    }

}
