package org.challenge.testautomationchallenge;

import io.restassured.response.Response;
import org.challenge.testautomationchallenge.config.RouteConfig;
import org.challenge.testautomationchallenge.service.http.GetHttp;
import org.challenge.testautomationchallenge.service.http.PostHttp;
import org.challenge.testautomationchallenge.services.AccountService;
import org.challenge.testautomationchallenge.services.ConsentService;
import org.challenge.testautomationchallenge.utils.ConsentStatusEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GetV1AccountsTest {
    @Autowired
    protected RouteConfig routeConfig;
    @Autowired
    protected GetHttp getHttp;
    @Autowired
    protected PostHttp postHttp;
    @Autowired
    protected ConsentService consentService;
    @Autowired
    protected AccountService accountService;
    protected String consentId = "";

    @Test
    void getAcctListValidBearer() {
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentId, ConsentStatusEnum.status.AUTHORISED.name());
        Response response = getHttp.getAccountConsentIdApi( routeConfig.getRouteAcctList()
                , consentId, 200);
        accountService.assertAccountListSuccessPayload(response, 200);
    }

    @Test
    void getAcctListNoToken() {
        Response response = getHttp.getApiNoToken(routeConfig.getRouteAcctList()
                , 401);
        accountService.assertAccountErrorMsgEqual(response, 401, "message", "Unauthorized");
    }

    @Test
    void getAcctListConsentRoleBearer() {
        Response response = getHttp.getConsentApi(routeConfig.getRouteAcctList()
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getAcctListAccountRoleBearer() {
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentId, ConsentStatusEnum.status.AUTHORISED.name());
        Response response = getHttp.getAccountConsentIdApi(routeConfig.getRouteAcctList()
                , consentId, 200);
        accountService.assertAccountListSuccessPayload(response, 200);
    }

    @Test
    void getAcctListCreditCardRoleBearer() {
        Response response = getHttp.getCreditCardApi(routeConfig.getRouteAcctList()
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getAcctListWrongUriPath() {
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentId, ConsentStatusEnum.status.AUTHORISED.name());
        Response response = getHttp.getAccountConsentIdApi(routeConfig.getRouteInvalidRouteAcctList()
                , consentId, 404);
        accountService.assertAccountErrorMsgEqual(response, 404, "message", "Not Found");
    }

    @Test
    void getAcctListClientWaitingAuthorisation() {
        consentId = consentService.postClientConsentId();
        Response response = getHttp.getAccountConsentIdApi(routeConfig.getRouteAcctList()
                , consentId, 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getAcctListConsentIdNull() {
        Response response = getHttp.getApi(routeConfig.getRouteAcctList()
                , 401);
        accountService.assertAccountErrorMsgEqual(response, 401, "message", "Unauthorized");
        accountService.assertAccountErrorMsgContains(response, 401, "message"
                , "Consent Id not present on the request");
    }

    @Test
    void getAcctListConsentIdPassed() {
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentId, ConsentStatusEnum.status.AUTHORISED.name());
        Response response = getHttp.getAccountConsentIdApi(routeConfig.getRouteAcctList()
                , consentId, 200);
        accountService.assertAccountListSuccessPayload(response, 200);
    }

    @Test
    void getAcctListRejected() {
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentId, ConsentStatusEnum.status.REJECTED.name());
        Response response = getHttp.getAccountConsentIdApi(routeConfig.getRouteAcctList()
                , consentId, 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

}
