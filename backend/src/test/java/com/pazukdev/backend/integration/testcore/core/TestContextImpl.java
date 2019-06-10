package com.pazukdev.backend.integration.testcore.core;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@Data
public class TestContextImpl implements TestContext {

    private final WebDriver driver;

}
