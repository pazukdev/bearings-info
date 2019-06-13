package com.pazukdev.backend.integration.testcore.core.config.waiting;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Aliaksei Voitsel
 *
 * Wait for Ajax call to complete
 */
public class A4JWaiting implements Waiting {

    private final WebDriverWait webDriverWait;
    private final JavascriptExecutor jsExecutor;

    public A4JWaiting(final WebDriverWait webDriverWait, final JavascriptExecutor jsExecutor) {
        this.webDriverWait = webDriverWait;
        this.jsExecutor = jsExecutor;
    }

    @Override
    public void doWait() {
        webDriverWait.until(x -> (jsExecutor).executeScript(
                "var sum = 0;"
                        + "for (const [key, value] of Object.entries(A4J.AJAX._requestsCounts)) { sum = sum + value; };"
                        + "return sum == 0;")
                .equals(true));
    }
}
