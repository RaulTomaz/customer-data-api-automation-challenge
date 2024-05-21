package org.challenge.testautomationchallenge.services;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.*;

@Component
public class AccountService {

    public void assertAccountListSuccessPayload(Response response, int statusCode){
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(statusCode))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("data.id", hasSize(2))
                .body("data.bank", hasItems("Nubank", "Itau"))
                .body("data.accountNumero", hasSize(2))
                .body("links", notNullValue())
                .body("meta.totalRecords", equalTo(2))
                .body("meta.totalPages", greaterThan(0));
    }

    public void assertAccountIdSuccessPayload(Response response, int statusCode){
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(statusCode))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("data.id", notNullValue())
                .body("data.bank", equalTo("Nubank"))
                .body("data.accountNumero", notNullValue())
                .body("links", notNullValue())
                .body("meta.totalRecords", greaterThan(0))
                .body("meta.totalPages", greaterThan(0));
    }

    public void assertAccountErrorMsgEqual(Response response, int statusCode, String attribute, String value){
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(statusCode))
                .extract()
                .response()
                .then()
                .assertThat()
                .body(attribute, equalTo(value));
    }

    public void assertAccountErrorMsgContains(Response response, int statusCode, String attribute, String value){
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(statusCode))
                .extract()
                .response()
                .then()
                .assertThat()
                .body(attribute, contains(value));
    }

}
