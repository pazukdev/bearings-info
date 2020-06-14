package com.pazukdev.backend.integration.cucumber.step;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import cucumber.api.java.en.Given;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class GeneralCucumberStep {

    private final TestContext context;

    @Given("app main page is opened")
    public void openMainPage() {
//        final HomePage homePage = new GetPageAction<>(context, HomePage.class).perform();
//        context.put("mainPage", homePage);
    }

}
