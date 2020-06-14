package com.pazukdev.backend.integration.testcore.util;

import com.pazukdev.backend.integration.testcore.constant.Constant;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ActionUtil {

    public static void sleep() {
        sleep(Constant.WaitTime.PAGE_LOAD);
    }

    public static void sleep(final int ms) {
        try {
            Thread.sleep(ms);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement waitForElement(final TestContext context, final Supplier<WebElement> supp) {
        return context.getWaitForElement().ignoring(NoSuchElementException.class).until(x -> supp.get());
    }

    public static void waitForAJAX(final TestContext context) {
        final JavascriptExecutor js = context.getJavascriptExecutor();
        context.getWaitForElement().until(x -> js.executeScript("return jQuery.active == 0").equals(true));
    }

    public static void waitForPageToLoad(final TestContext context) {
        context.getWaitForElement()
                .until(x -> (context.getJavascriptExecutor()).executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForDynamicChanges(final TestContext context) {
        context.getWaiting().doWait();
    }

    public static <V> void waitOptionalWithTimeout(final TestContext context,
                                                   final Function<? super WebDriver, V> conditionIsTrue,
                                                   final int timeoutInSeconds) {
        try {
            new WebDriverWait(context.getDriver(), timeoutInSeconds).until(conditionIsTrue);
        } catch (final TimeoutException e) {
            TestContext.LOGGER.warn("waitOptionalWithTimeout: ", e);
        }
    }

    public static void selectDropDownByText(final TestContext context,
                                            final WebElement element, 
                                            final String value) {
        final Select select = new Select(element);
        select.selectByVisibleText(value);
        waitForDynamicChanges(context);
    }

    public static void selectDropDownByValue(final TestContext context,
                                             final WebElement element,
                                             final String value) {
        final Select select = new Select(element);
        select.selectByValue(value);
        waitForDynamicChanges(context);
    }

    public static void clearInputAndSendKeys(final TestContext context,
                                             final WebElement input, 
                                             final String text) {
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(text);
        waitForDynamicChanges(context);
    }

    public static void performJSClick(final TestContext context, final WebElement element) {
        final JavascriptExecutor executor = (JavascriptExecutor) context.getDriver();
        executor.executeScript("arguments[0].click();", element);
        waitForDynamicChanges(context);
    }

    public static void setCheckboxSelection(final TestContext context, 
                                            final WebElement checkbox, 
                                            final boolean value) {
        if (checkbox.isSelected() != value) {
            clickAndWaitForLoading(context, checkbox);
        }
    }

    public static void clickAndWaitForLoading(final TestContext context, final WebElement element) {
        element.click();
        waitForDynamicChanges(context);
    }

    public static void fillInputOptionally(final TestContext context,
                                           final Supplier<WebElement> element,
                                           final String text) {
        if (text != null) {
            clearInputAndSendKeys(context, element.get(), text);
        }
    }

    public static boolean selectDropdownOptionally(final TestContext context,
                                                   final Supplier<WebElement> select,
                                                   final String text) {
        if (text != null) {
            selectDropDownByText(context, select.get(), text);
        }
        return text != null;
    }

    public static <T extends Page> T getPage(final TestContext context, final T page) {
        context.getDriver().get(page.getUrl());
        page.initElements(context);
        return page;
    }

}
