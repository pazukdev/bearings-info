package com.pazukdev.backend.integration.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature/",
        glue = "src/test/java/"
)
public class CucumberIT {
}
