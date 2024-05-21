package org.challenge.testautomationchallenge;

import io.restassured.response.Response;
import org.challenge.testautomationchallenge.config.RouteConfig;
import org.challenge.testautomationchallenge.services.AccountService;
import org.challenge.testautomationchallenge.services.ConsentService;
import org.challenge.testautomationchallenge.utils.ConsentStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.challenge.testautomationchallenge.service.http.GetHttp;

@SpringBootTest
public class GetV1AccountIdTest {

    @Autowired
    protected RouteConfig routeConfig;
    @Autowired
    protected GetHttp getHttp;
    @Autowired
    protected ConsentService consentService;
    @Autowired
    protected AccountService accountService;
    String consentId = "";

    @Test
    void getNonExistentAcctIdValidBearer() {
        Response response = getHttp.getApi(routeConfig.getRouteAcctId() + routeConfig.getRouteInvalidAcctId()
                , 404);
        accountService.assertAccountErrorMsgEqual(response, 404, "message", "Not Found");
    }

    @Test
    void getExistentAcctIdValidBearer(){
        Response response = getHttp.getApi(routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 200);
        accountService.assertAccountIdSuccessPayload(response, 200);
    }

    @Test
    void getInvalidUuidAcctId(){
        Response response = getHttp.getApi(routeConfig.getRouteAcctId() + routeConfig.getRouteInvalidUuidAcctId()
                , 400);
        accountService.assertAccountErrorMsgEqual(response, 400, "message", "Bad Request");
    }

    @Test
    void getWithNoTokenAcctId(){
        Response response = getHttp.getApiNoToken(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 401);
        accountService.assertAccountErrorMsgEqual(response, 401, "message", "Unauthorized");
    }

    @Test
    void getWithConsentRoleAcctId(){
        Response response = getHttp.getConsentApi(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithAccountRoleAcctId(){
        Response response = getHttp.getApi(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 200);
        accountService.assertAccountIdSuccessPayload(response, 200);
    }

    @Test
    void getWithCreditCardsRoleAcctId(){
        Response response = getHttp.getCreditCardApi(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithWrongPathUriAcctId(){
        Response response = getHttp.getApi(
                routeConfig.getRouteInvalidRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 404);
        accountService.assertAccountErrorMsgEqual(response, 404, "message", "Not Found");
    }

    @Test
    void getWithClientWaitAuthorisationAcctId(){
        Response response = getHttp.getApi(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithClientRejectedAcctId(){
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentService.postClientConsentId(), ConsentStatusEnum.status.REJECTED.name());
        Response response = getHttp.getAccountConsentIdApi(
                routeConfig.getRouteAcctId() + routeConfig.getRouteValidAcctId()
                , consentId,403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

}
