package org.challenge.testautomationchallenge.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthHeaderUtils {

    public Map<String, String> createHeaderAuth(){
        Map<String, String> authHeader = new HashMap<>();

        authHeader.put("Authorization", "Bearer " + getEncodedBearerToken());
        System.out.println(getEncodedBearerToken());
        return authHeader;
    }

    public String getEncodedBearerToken(){
        return encodeChallengeHeader() + "." + encodeChallengePayload("accounts") + ".";
    }

    public String encodeChallengeHeader(){
        String Json = "{\"alg\": \"none\",\"typ\": \"JWT\"}";
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

    public String encodeChallengePayload(String scope){
        String Json = "{\"scope\": \""+scope+"\",\"client_id\": \"client1\"}";
        return Base64.getEncoder().encodeToString(Json.getBytes(StandardCharsets.UTF_8));
    }

}
