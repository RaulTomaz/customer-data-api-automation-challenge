package org.challenge.testautomationchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
class GetV1AccountsTest {

    @Test
    void getAcctListValidBearer() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(200)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(200))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("id", hasSize(2))
                .body("bank", hasItems("Nubank", "Itau"))
                .body("accountNumero", hasSize(2))
                .body("links", hasSize(2))
                .body("meta.totalRecords", greaterThan(2))
                .body("meta.totalPages", greaterThan(0));

    }

    @Test
    void getAcctListNoToken() {
        given()
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(401)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(401))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Unauthorized"));

    }

    @Test
    void getAcctListConsentRoleBearer() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjb25zZW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(403)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Forbidden"));

    }

    @Test
    void getAcctListAccountRoleBearer() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQxIn0=.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(200)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(200))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("id", hasSize(2))
                .body("bank", hasItems("Nubank", "Itau"))
                .body("accountNumero", hasSize(2))
                .body("links", hasSize(2))
                .body("meta.totalRecords", greaterThan(2))
                .body("meta.totalPages", greaterThan(0));

    }

    @Test
    void getAcctListCreditCardRoleBearer() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MSJ9.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(403)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Forbidden"));

    }

    @Test
    void getAcctListWrongUriPath() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MSJ9.")
                .request("GET", "http://localhost:8080/test-api/accounts/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(404)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(404))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Not Found"));

    }

    @Test
    void getAcctListClientWaitingAuthorisation() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MiJ9.")
                .request("GET", "http://localhost:8080/test-api/accounts/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(403)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(403))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Forbidden"));
    }

    @Test
    void getAcctListConsentIdNull() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJjcmVkaXQtY2FyZHMiLCJjbGllbnRfaWQiOiAiY2xpZW50MiJ9.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(400)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(400))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("message", equalTo("Bad Request"))
                .body("message", contains("Consent Id is null"));
    }

    @Test
    void getAcctListConsentIdPassed() {
        given()
                .header("Authorization",
                        "Bearer eyJhbGciOiAibm9uZSIsInR5cCI6ICJKV1QifQ==.eyJzY29wZSI6ICJhY2NvdW50cyIsImNsaWVudF9pZCI6ICJjbGllbnQyIiwgImNvbnNlbnRJZCI6InVybjpiYW5rOjQ3ODRkYmVhLTBiYzQtNDI2Mi04MzYyLTQ1OTBhZGIyNTg0ZSJ9.")
                .request("GET", "http://localhost:8080/test-api/account/v1/accounts")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(200)
                .extract()
                .response()
                .then()
                .log()
                .ifStatusCodeMatches(equalTo(200))
                .extract()
                .response()
                .then()
                .assertThat()
                .body("id", hasSize(2))
                .body("bank", hasItems("Nubank", "Itau"))
                .body("accountNumero", hasSize(2))
                .body("links", hasSize(2))
                .body("meta.totalRecords", greaterThan(2))
                .body("meta.totalPages", greaterThan(0));
    }


}
