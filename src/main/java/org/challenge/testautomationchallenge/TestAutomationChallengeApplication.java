package org.challenge.testautomationchallenge;

import org.challenge.testautomationchallenge.config.BaseUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TestAutomationChallengeApplication implements CommandLineRunner {

    @Autowired
    BaseUrlConfig baseUrlConfig;

    public static void main(String[] args) {
        SpringApplication.run(TestAutomationChallengeApplication.class, args);
    }

    public void run(String... args) {
        System.out.println("base url: " + baseUrlConfig.getBaseUrl());
    }

}
