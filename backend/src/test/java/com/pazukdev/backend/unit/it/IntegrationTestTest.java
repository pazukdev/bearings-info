package com.pazukdev.backend.unit.it;

import com.pazukdev.backend.integration.testcore.asserter.Asserter;
import com.pazukdev.backend.integration.testcore.asserter.PageLoadAsserter;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.core.TestContextImpl;
import com.pazukdev.backend.integration.testcore.page.Page;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationTestTest {

    @InjectMocks
    private TestContextImpl context;
    @Mock
    private WebDriver driverCreatedByFactory;

    @Test
    public void testContextTest() {
        final WebDriver driver1 = context.getDriver();
        final WebDriver driver2 = context.getDriver();

        Assert.assertNotNull(driver1);
        Assert.assertSame(driver1, driver2);
    }

    @Test
    public void pageLoadAsserterTest() {
        final Page badPage = new TestPage();
        final Asserter asserter = new PageLoadAsserter(context, badPage);

       AssertionError expectedAssertionError = null;
        try {
            asserter.perform();
        } catch (final AssertionError error) {
            expectedAssertionError = error;
        }

        Assert.assertNotNull(expectedAssertionError);
    }

    public static class TestPage implements Page {

        @Override
        public String getURL() {
            return  "https://www.someurl.com";
        }

        @Override
        public WebElement getControlElement() {
            return null;
        }

        @Override
        public void initElements(TestContext context) {
            // no need to implement
        }
    }

}
