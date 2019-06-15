package com.pazukdev.backend.integration.cucumber.step;

import com.pazukdev.backend.integration.testcore.action.GetPageAction;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.MainPage;
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
        final MainPage mainPage = new GetPageAction<>(context, MainPage.class).perform();
        context.put("mainPage", mainPage);
    }

}
