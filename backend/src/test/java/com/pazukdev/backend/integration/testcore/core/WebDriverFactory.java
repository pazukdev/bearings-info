package com.pazukdev.backend.integration.testcore.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author Siarhei Sviarkaltsau
 */
public class WebDriverFactory {

    public WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/chromedriver");
        final ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless", "disable-gpu", "--no-sandbox", "disable-dev-tools", "window-size=1024,768");
        return new ChromeDriver(chromeOptions);
    }

}
