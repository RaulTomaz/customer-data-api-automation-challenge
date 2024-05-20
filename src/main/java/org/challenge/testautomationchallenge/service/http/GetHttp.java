package org.challenge.testautomationchallenge.service.http;

import io.restassured.response.Response;
import org.springframework.stereotype.Service;
import org.challenge.testautomationchallenge.service.SpecBuilder;

import static io.restassured.RestAssured.*;

@Service
public class GetHttp extends SpecBuilder {

    public Response getApi(String apiPath, int statusCode){
        return given(buildRequestSpec())
                .when()
                .get(apiPath)
                .then()
                .spec(getResponseSpec())
                .log().ifValidationFails().statusCode(statusCode)
                .extract().response();
    }

}
