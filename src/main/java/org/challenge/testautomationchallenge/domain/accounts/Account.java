package org.challenge.testautomationchallenge.domain.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {
    private String scope;
    @JsonProperty("client_id")
    private String clientId;

}
