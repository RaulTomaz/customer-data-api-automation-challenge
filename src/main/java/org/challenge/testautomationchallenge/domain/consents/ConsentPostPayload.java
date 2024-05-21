package org.challenge.testautomationchallenge.domain.consents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.LocalDateTime;

@Data
public class ConsentPostPayload {
    private String permissions;
    private String expirationDateTime;

}
