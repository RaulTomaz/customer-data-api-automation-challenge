package org.challenge.testautomationchallenge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "route")
@Getter
@Setter
public class RouteConfig {
    private String routeAcctId;
    private String routeAcctList;
    private String routeInvalidRouteAcctList;
    private String routeInvalidRouteAcctId;
    private String routeInvalidAcctId;
    private String routeValidAcctId;
    private String routeInvalidUuidAcctId;


}
