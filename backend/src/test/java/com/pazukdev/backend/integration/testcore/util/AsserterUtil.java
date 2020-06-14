package com.pazukdev.backend.integration.testcore.util;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public class AsserterUtil {

    public static void assertPageLoaded(final TestContext context, final Page page) {
        assertPageLoaded(context, page, null, null);
    }

    public static void assertPageLoaded(final TestContext context,
                                        final Page page,
                                        final String checkValue,
                                        final String checkText) {
        page.initElements(context);
        assertElement(page.getControlElement(), checkValue, checkText);
    }

    public static void assertElement(final WebElement element,
                                     final String checkValue,
                                     final String checkText) {
        assertValue(element, checkValue);
        assertText(element, checkText);
    }

    public static void assertValue(final WebElement element, final String checkValue) {
        Assert.assertNotNull(element);
        Assert.assertTrue(element.isDisplayed());
        if (checkValue != null) {
            Assert.assertEquals(checkValue, element.getAttribute("value"));
        }
    }

    public static void assertText(final WebElement element, final String checkText) {
        Assert.assertNotNull(element);
        Assert.assertTrue(element.isDisplayed());
        if (checkText != null) {
            Assert.assertEquals(checkText, element.getText());
        }
    }

}
