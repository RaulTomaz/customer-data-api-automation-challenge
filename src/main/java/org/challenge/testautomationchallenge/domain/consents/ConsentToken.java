package org.challenge.testautomationchallenge.domain.consents;

import lombok.Data;

@Data
public class ConsentToken {
    private String scope;
    private String client_id;

}
