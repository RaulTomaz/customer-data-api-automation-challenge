package org.challenge.testautomationchallenge.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.challenge.testautomationchallenge.domain.consents.*;

import java.io.File;
import java.time.Instant;

public class ConsentPojo {
    ObjectMapper objectMapper = new ObjectMapper();
    ConsentToken consentToken = new ConsentToken();
    ConsentPostPayload consentPostPayload = new ConsentPostPayload();
    ConsentPutPayload consentPutPayload = new ConsentPutPayload();
    ConsentPostData consentPostData = new ConsentPostData();
    ConsentPutData consentPutData = new ConsentPutData();
    File file = new File("src/main/java/org/challenge/testautomationchallenge/data/payload-consents.json");

    @SneakyThrows
    public void deserializeTokenPayloadConsent() {
        consentToken = objectMapper.readValue(file, ConsentToken.class);
    }

    @SneakyThrows
    public String returnSerializePostPayloadConsent(){
        Instant instant = Instant.now();
        consentPostPayload.setPermissions("ACCOUNTS_READ");
        consentPostPayload.setExpirationDateTime(instant.toString());
        consentPostData.setData(consentPostPayload);
        return objectMapper.writeValueAsString(consentPostData);
    }

    @SneakyThrows
    public String returnSerializePutPayloadConsent(String status){
        consentPutPayload.setStatus(status);
        consentPutData.setData(consentPutPayload);
        return objectMapper.writeValueAsString(consentPutData);
    }

    @SneakyThrows
    public String returnSerializeTokenPayloadConsent() {
        deserializeTokenPayloadConsent();
        return objectMapper.writeValueAsString(consentToken);
    }

}
