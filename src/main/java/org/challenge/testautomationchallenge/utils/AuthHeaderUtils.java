package org.challenge.testautomationchallenge.utils;

import jakarta.annotation.PostConstruct;
import org.challenge.testautomationchallenge.pojo.AccountPojo;
import org.challenge.testautomationchallenge.pojo.ConsentPojo;
import org.challenge.testautomationchallenge.pojo.CreditCardPojo;
import org.challenge.testautomationchallenge.pojo.HeaderPojo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthHeaderUtils {

    HeaderPojo headerPojo = new HeaderPojo();
    AccountPojo accountPojo = new AccountPojo();
    ConsentPojo consentPojo = new ConsentPojo();
    CreditCardPojo creditCardPojo = new CreditCardPojo();

    public Map<String, String> createHeaderAuth(String encodePayloadMethod){
        Map<String, String> authHeader = new HashMap<>();

        authHeader.put("Authorization", "Bearer " + encodePayloadMethod);
        return authHeader;
    }

    public String getEncodedBearerTokenAcct(){
        return encodeChallengeHeader() + "." + encodeChallengePayloadAcct() + ".";
    }

    public String getEncodedBearerTokenConsent(){
        return encodeChallengeHeader() + "." + encodeChallengePayloadConsent() + ".";
    }

    public String getEncodedBearerTokenCreditCard(){
        return encodeChallengeHeader() + "." + encodeChallengePayloadCreditCard() + ".";
    }

    public String getEncodedBearerTokenAcctConsentId(String consentId){
        return encodeChallengeHeader() + "." + encodeChallengePayloadAcctConsentId(consentId) + ".";
    }

    public String encodeChallengeHeader(){
        String json = headerPojo.returnSerializeHeader();
        return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    public String encodeChallengePayloadConsent(){
        String Json = consentPojo.returnSerializeTokenPayloadConsent();
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

    public String encodeChallengePayloadCreditCard(){
        String Json = creditCardPojo.returnSerializePayloadCreditCard();
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

    public String encodeChallengePayloadAcct(){
        String Json = accountPojo.returnSerializePayloadAcct();
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

    public String encodeChallengePayloadAcctConsentId(String consentId){
        String Json = accountPojo.returnSerializePayloadAcctConsentId(consentId);
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

}
