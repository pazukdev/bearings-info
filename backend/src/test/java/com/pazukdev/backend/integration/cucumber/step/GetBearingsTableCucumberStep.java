package com.pazukdev.backend.integration.cucumber.step;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.AbstractPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class GetBearingsTableCucumberStep {

    private final TestContext context;
    private AbstractPage bearingPage;

    @When("user opens bearings info")
    public void openBearingsTable() {
//        final RouteNode<HomePage> mainPageRouteNode = new RouteNode<>(HomePage.class, "bearingsButton");
//        bearingPage = new GetPageAction<AbstractPage>(context, new Route<>(mainPageRouteNode)).perform();
    }

    @Then("user sees a table of bearings")
    public void validateBearingsTableIsDisplayed() {
//        final WebElement bearingsTable = bearingPage.getProductsTable();
//        Assert.assertNotNull(bearingsTable);
//        Assert.assertTrue(bearingsTable.isDisplayed());
    }

}
