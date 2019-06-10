package com.pazukdev.backend.integration.testcore.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class WebdriverFactory {

    @Bean
    public WebDriver createRemoteDriver() {
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/chromedriver");
        return new ChromeDriver();
    }

}
