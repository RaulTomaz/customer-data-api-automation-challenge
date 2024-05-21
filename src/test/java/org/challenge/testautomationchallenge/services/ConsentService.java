package org.challenge.testautomationchallenge.services;

import io.restassured.response.Response;

import org.challenge.testautomationchallenge.pojo.ConsentPojo;
import org.challenge.testautomationchallenge.service.http.PostHttp;
import org.challenge.testautomationchallenge.service.http.PutHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Component
public class ConsentService {
    @Autowired
    protected PostHttp postHttp;
    @Autowired
    protected PutHttp putHttp;
    ConsentPojo consentPojo = new ConsentPojo();

    public String postClientConsentId(){
        Response response = postHttp.postApi("consents/v1/consents"
                , consentPojo.returnSerializePostPayloadConsent(), 201);
        return "consent:" + response.jsonPath().get("data.consentId").toString();
    }

    public void putClientStatus(String consentId, String status){
        Response response = putHttp.putApi("consents/v1/consents/" +
                        (consentId.replace("consent:", ""))
                , consentPojo.returnSerializePutPayloadConsent(status), 200);
        assertThat(response.jsonPath().get("data.status"), is(equalTo(status)));

    }

}
