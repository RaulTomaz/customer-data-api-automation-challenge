package org.challenge.testautomationchallenge.service.http;

import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.challenge.testautomationchallenge.config.BaseUrlConfig;
import org.challenge.testautomationchallenge.service.SpecBuilder;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@Service
public class PutHttp extends SpecBuilder {

    public Response putApi(String apiPath, String payload, int statusCode){
        return given(buildRequestSpecConsent())
                .when()
                .body(payload)
                .put(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

}
