package org.challenge.testautomationchallenge.service.http;

import io.restassured.response.Response;
import org.springframework.stereotype.Service;
import org.challenge.testautomationchallenge.service.SpecBuilder;

import java.util.Map;

import static io.restassured.RestAssured.*;

@Service
public class GetHttp extends SpecBuilder {

    public Response getApi(String apiPath, int statusCode){
        return given(buildRequestSpecAcct())
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

    public Response getApiNoToken(String apiPath, int statusCode){
        return given(buildRequestSpec())
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

    public Response getAccountConsentIdApi(String apiPath, String consentId, int statusCode){
        return given(buildRequestSpecAccountConsentId(consentId))
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

    public Response getConsentApi(String apiPath, int statusCode){
        return given(buildRequestSpecConsent())
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

    public Response getCreditCardApi(String apiPath, int statusCode){
        return given(buildRequestSpecCreditCard())
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

}
