package org.challenge.testautomationchallenge;

import io.restassured.response.Response;
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
    protected GetHttp getHttp;
    @Autowired
    protected ConsentService consentService;
    @Autowired
    protected AccountService accountService;
    String consentId = "";

    @Test
    void getNonExistentAcctIdValidBearer() {
        Response response = getHttp.getApi("account/v1/account/e58c4536-454a-4608-8bf8-f0389d2ed431", 404);
        accountService.assertAccountErrorMsgEqual(response, 404, "message", "Not Found");
    }

    @Test
    void getExistentAcctIdValidBearer(){
        Response response = getHttp.getApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 200);
        accountService.assertAccountIdSuccessPayload(response, 200);
    }

    @Test
    void getInvalidUuidAcctId(){
        Response response = getHttp.getApi("account/v1/account/aaa", 400);
        accountService.assertAccountErrorMsgEqual(response, 400, "message", "Bad Request");
    }

    @Test
    void getWithNoTokenAcctId(){
        Response response = getHttp.getApiNoToken("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 401);
        accountService.assertAccountErrorMsgEqual(response, 401, "message", "Unauthorized");
    }

    @Test
    void getWithConsentRoleAcctId(){
        Response response = getHttp.getConsentApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithAccountRoleAcctId(){
        Response response = getHttp.getApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74", 200);
        accountService.assertAccountIdSuccessPayload(response, 200);
    }

    @Test
    void getWithCreditCardsRoleAcctId(){
        Response response = getHttp.getCreditCardApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithWrongPathUriAcctId(){
        Response response = getHttp.getApi("accounts/v1/accounts/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 404);
        accountService.assertAccountErrorMsgEqual(response, 404, "message", "Not Found");
    }

    @Test
    void getWithClientWaitAuthorisationAcctId(){
        Response response = getHttp.getApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , 403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

    @Test
    void getWithClientRejectedAcctId(){
        consentId = consentService.postClientConsentId();
        consentService.putClientStatus(consentService.postClientConsentId(), ConsentStatusEnum.status.REJECTED.name());
        Response response = getHttp.getAccountConsentIdApi("account/v1/account/87caf37b-f70f-440c-bacd-3b9399ca5d74"
                , consentId,403);
        accountService.assertAccountErrorMsgEqual(response, 403, "message", "Forbidden");
    }

}
