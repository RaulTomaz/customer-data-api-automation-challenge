package org.challenge.testautomationchallenge;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.challenge.testautomationchallenge.service.http.GetHttp;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class GetV1AccountIdTest {

    @Autowired
    protected GetHttp getHttp;

    @Test
    void getNonExistentAcctIdValidBearer() {
        Response response = getHttp.getApi("account/v1/account/e58c4536-454a-4608-8bf8-f0389d2ed431", 404);
        response.then().statusCode(404)
        .assertThat().body("message", equalTo("Not Found"));

    }

    @Test
    void getExistentAcctIdValidBearer(){
        Response response = getHttp.getApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 200);
        response.then().statusCode(200)
                .log().body()
                .assertThat()
                .body("data.id", notNullValue())
                .body("data.bank", equalTo("Nubank"))
                .body("data.accountNumero", notNullValue())
                .body("links", notNullValue())
                .body("meta.totalRecords", greaterThan(0))
                .body("meta.totalPages", greaterThan(0));

    }

    @Test
    void getInvalidUuidAcctId(){
        Response response = getHttp.getApi("account/v1/account/aaa", 400);
        response.then().statusCode(404)
                .assertThat()
                .body("message", equalTo("Bad Request"));
    }

    @Test
    void getWithNoTokenAcctId(){
        Response response = getHttp.getApiNoToken("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 401);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(401))
                .body("message", equalTo("Unauthorized"))
                .extract()
                .response();
    }

    @Test
    void getWithConsentRoleAcctId(){
        Response response = getHttp.getConsentApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 403);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

    @Test
    void getWithAccountRoleAcctId(){
        Response response = getHttp.getApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 200);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(200))
                .assertThat()
                .body("data.id", notNullValue())
                .body("data.bank", equalTo("Nubank"))
                .body("data.accountNumero", notNullValue())
                .body("links", notNullValue())
                .body("meta.totalRecords", greaterThan(0))
                .body("meta.totalPages", greaterThan(0))
                .extract()
                .response();
    }

    @Test
    void getWithCreditCardsRoleAcctId(){
        Response response = getHttp.getCreditCardApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 403);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

    @Test
    void getWithWrongPathUriAcctId(){
        Response response = getHttp.getCreditCardApi("accounts/v1/accounts/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 404);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(404))
                .body("message", equalTo("Not Found"))
                .extract()
                .response();
    }

    @Test
    void getWithClientWaitAuthorisationAcctId(){
        Response response = getHttp.getApi("accounts/v1/accounts/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 403);
        response.then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

}
