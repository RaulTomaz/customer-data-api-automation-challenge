package org.challenge.testautomationchallenge;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.challenge.testautomationchallenge.service.http.GetHttp;

import static io.restassured.RestAssured.given;
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
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().statusCode(200)
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
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/aaa")
                .then().log().ifValidationFails().statusCode(400)
                .extract().response();
    }

    @Test
    void getWithNoTokenAcctId(){
        given()
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(401)
                .extract().response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(401))
                .body("message", equalTo("Unauthorized"))
                .extract()
                .response();
    }

    @Test
    void getWithConsentRoleAcctId(){
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjb25zZW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(403)
                .extract().response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

    @Test
    void getWithAccountRoleAcctId(){
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(200)
                .extract().response()
                .then()
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
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MSJ9.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(403)
                .extract().response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

    @Test
    void getWithWrongPathUriAcctId(){
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MSJ9.")
                .request("GET", "http://localhost:8080/test-api/accounts/v1/accounts/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(404)
                .extract().response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(404))
                .body("message", equalTo("Not Found"))
                .extract()
                .response();
    }

    @Test
    void getWithClientWaitAuthorisationAcctId(){
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQyIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74")
                .then().log().ifValidationFails().statusCode(403)
                .extract().response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .body("message", equalTo("Forbidden"))
                .extract()
                .response();
    }

}
