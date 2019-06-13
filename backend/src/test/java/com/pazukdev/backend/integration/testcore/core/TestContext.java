package com.pazukdev.backend.integration.testcore.core;

import com.pazukdev.backend.integration.testcore.core.config.Config;
import com.pazukdev.backend.integration.testcore.core.config.waiting.Waiting;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface TestContext {

    Config getConfig();

    WebDriver getDriver();

    JavascriptExecutor getJavascriptExecutor();

    WebDriverWait getWaitForElement();

    Waiting getWaiting();

    <T> T get(Class<T> valueClass);

    <T> T get(String key);

    void put(Object value);

    void put(String key, Object value);

}
