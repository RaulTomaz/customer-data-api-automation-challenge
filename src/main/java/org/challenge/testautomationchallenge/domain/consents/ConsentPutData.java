package org.challenge.testautomationchallenge.domain.consents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConsentPutData {
    @JsonProperty("data")
    private ConsentPutPayload data;

}
