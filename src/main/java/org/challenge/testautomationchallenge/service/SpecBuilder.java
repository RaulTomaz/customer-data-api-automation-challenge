package org.challenge.testautomationchallenge.service;

import org.challenge.testautomationchallenge.config.BaseUrlConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.challenge.testautomationchallenge.utils.AuthHeaderUtils;

@Service
public class SpecBuilder {

    @Autowired
    private BaseUrlConfig baseUrlConfig;
    private final AuthHeaderUtils authHeaderUtils = new AuthHeaderUtils();

    public RequestSpecification buildRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(baseUrlConfig.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAuth(httpBuilder -> {
                    httpBuilder.setHeaders(authHeaderUtils.createHeaderAuth());
                })
                .addFilter(new AllureRestAssured())
                .build();
    }

    public ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().build();
    }


}
