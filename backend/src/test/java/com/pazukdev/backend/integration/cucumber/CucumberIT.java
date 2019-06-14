package com.pazukdev.backend.integration.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/feature",
        glue = "src/test/java/com/pazukdev/backend/integration/cucumber/step"
)
public class CucumberIT {
}
