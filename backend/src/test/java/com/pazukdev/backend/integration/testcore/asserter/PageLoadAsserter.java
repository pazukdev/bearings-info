package com.pazukdev.backend.integration.testcore.asserter;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class PageLoadAsserter implements Asserter {

    private final TestContext context;
    private final Page page;

    @Override
    public void perform() {
        PageFactory.initElements(context.getDriver(), page.getClass());

        assertPageLoaded(page);
    }

    private void assertPageLoaded(final Page page) {
        final WebElement controlElement = page.getControlElement();

        Assert.assertNotNull(controlElement);
        Assert.assertTrue(controlElement.isDisplayed());
    }
}
