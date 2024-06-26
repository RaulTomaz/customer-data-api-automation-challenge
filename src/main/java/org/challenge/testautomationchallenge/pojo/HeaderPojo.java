package org.challenge.testautomationchallenge.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.challenge.testautomationchallenge.domain.Header.Header;

import java.io.File;

public class HeaderPojo {
    ObjectMapper objectMapper = new ObjectMapper();
    Header header = new Header();
    File file = new File("src/main/java/org/challenge/testautomationchallenge/data/header.json");

    @SneakyThrows
    public void returnDeserializeHeader() {
        header = objectMapper.readValue(file, Header.class);
    }

    @SneakyThrows
    public String returnSerializeHeader() {
        returnDeserializeHeader();
        return objectMapper.writeValueAsString(header);
    }

}
