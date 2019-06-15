package com.pazukdev.backend.integration.cucumber.step;

import com.pazukdev.backend.integration.testcore.action.GetPageAction;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.AbstractPage;
import com.pazukdev.backend.integration.testcore.page.MainPage;
import com.pazukdev.backend.integration.testcore.route.Route;
import com.pazukdev.backend.integration.testcore.route.RouteNode;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Data;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class GetBearingsTableCucumberStep {

    private final TestContext context;
    private AbstractPage bearingPage;

    @When("user opens bearings info")
    public void openBearingsTable() {
        final RouteNode<MainPage> mainPageRouteNode = new RouteNode<>(MainPage.class, "bearingsButton");
        bearingPage = new GetPageAction<AbstractPage>(context, new Route<>(mainPageRouteNode)).perform();
    }

    @Then("user sees a table of bearings")
    public void validateBearingsTableIsDisplayed() {
        final WebElement bearingsTable = bearingPage.getProductsTable();
        Assert.assertNotNull(bearingsTable);
        Assert.assertTrue(bearingsTable.isDisplayed());
    }

}
