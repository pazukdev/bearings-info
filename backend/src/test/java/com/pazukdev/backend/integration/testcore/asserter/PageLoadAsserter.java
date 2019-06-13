package com.pazukdev.backend.integration.testcore.asserter;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public class PageLoadAsserter extends AbstractAsserter {

    public PageLoadAsserter(final TestContext context, final Page page) {
        super(context);
        this.page = page;
    }

    @Override
    public Boolean perform() {
        page.initElements(context);
        assertPageLoaded(page);

        return true;
    }

    private void assertPageLoaded(final Page page) {
        final WebElement controlElement = page.getControlElement();

        Assert.assertNotNull(controlElement);
        Assert.assertTrue(controlElement.isDisplayed());
    }

}
