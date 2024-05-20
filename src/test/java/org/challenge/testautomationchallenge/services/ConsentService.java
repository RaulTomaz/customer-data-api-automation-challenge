package org.challenge.testautomationchallenge.services;

import io.restassured.response.Response;
import org.challenge.testautomationchallenge.service.http.GetHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.equalTo;

@Component
public class ConsentService {
    @Autowired
    protected GetHttp getHttp;
    String consentId = "";

    public void getAwaitingAuthClientConsentId(){
        Response response = getHttp.getApi("account/v1/account/e58c4536-454a-4608-8bf8-f0389d2ed431", 404);
        response.then().statusCode(404)
                .assertThat().body("message", equalTo("Not Found"));
    }

}
