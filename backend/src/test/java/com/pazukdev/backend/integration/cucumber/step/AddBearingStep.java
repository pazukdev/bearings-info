package com.pazukdev.backend.integration.cucumber.step;

import com.pazukdev.backend.controller.BearingController;
import com.pazukdev.backend.entity.Bearing;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Siarhei Sviarkaltsau
 */
public class AddBearingStep extends AbstractCucumberStep {

    private Bearing bearing;
    private BearingController controller;

    public AddBearingStep(TestContext context) {
        super(context);
    }

    @When("^adds a new bearing with data: (.*), (.*), (.*), (.*)$")
    public void addBearing(final String name,
                           final String type,
                           final String rollingElement,
                           final String rollingElementsQuantity) {

        // TODO write me
    }

    @Then("user sees that the bearing added to table")
    public void validateBearingAdded() {
        // TODO write me
    }

}
