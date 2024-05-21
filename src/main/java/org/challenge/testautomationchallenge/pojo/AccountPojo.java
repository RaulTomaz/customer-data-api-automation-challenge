package org.challenge.testautomationchallenge.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.challenge.testautomationchallenge.domain.accounts.Account;

import java.io.File;

public class AccountPojo {

    ObjectMapper objectMapper = new ObjectMapper();
    Account account = new Account();
    File file = new File("src/main/java/org/challenge/testautomationchallenge/data/payload-accounts.json");

    @SneakyThrows
    public void deserializePayloadAcct() {
        account = objectMapper.readValue(file, Account.class);
    }

    @SneakyThrows
    public String returnSerializePayloadAcct() {
        deserializePayloadAcct();
        return objectMapper.writeValueAsString(account);
    }

    @SneakyThrows
    public String returnSerializePayloadAcctConsentId(String consentId) {
        deserializePayloadAcct();
        account.setScope(account.getScope() + " " + consentId);
        return objectMapper.writeValueAsString(account);
    }

}
