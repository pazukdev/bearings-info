package com.pazukdev.backend.integration.testcore.core;

import com.pazukdev.backend.integration.testcore.core.config.Config;
import com.pazukdev.backend.integration.testcore.core.config.DefaultConfig;
import com.pazukdev.backend.integration.testcore.core.config.waiting.A4JWaiting;
import com.pazukdev.backend.integration.testcore.core.config.waiting.Waiting;
import lombok.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wladimir Litvinov
 */
@Component
@Data
public class TestContextImpl implements TestContext {

    private final Config config;
    private final WebDriver driver;
    private final WebDriverWait waitForElement;
    private final JavascriptExecutor javascriptExecutor;
    private final Waiting waiting;
    private final Map<String, Object> storedData;

    public TestContextImpl() {
        final WebDriverFactory driverFactory = new WebDriverFactory();
        this.driver = driverFactory.createDriver();
        this.config = new DefaultConfig();
        this.waitForElement = new WebDriverWait(driver, this.config.getWaitTimeout());
        this.javascriptExecutor = (JavascriptExecutor) driver;
        this.waiting = new A4JWaiting(waitForElement, javascriptExecutor);
        this.storedData = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Class<T> valueClass) {
        for (final Object o : storedData.values()) {
            if (o.getClass() == valueClass) return (T) o;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final String key) {
        if (storedData.get(key) == null) {
            return null;
        }
        return (T) storedData.get(key);
    }

    @Override
    public void put(final Object value) {
        put(value.getClass().getSimpleName(), value);
    }

    @Override
    public void put(final String key, final Object value) {
        storedData.put(key, value);
    }

}
