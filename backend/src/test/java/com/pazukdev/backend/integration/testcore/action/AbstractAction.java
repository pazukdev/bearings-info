package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import com.pazukdev.backend.integration.testcore.util.PageUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public abstract class AbstractAction<T extends Page> implements Action<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractAction.class);

    protected final TestContext context;

    protected WebElement waitForElement(final Supplier<WebElement> supp) {
        return context.getWaitForElement().ignoring(NoSuchElementException.class).until(x -> supp.get());
    }

    public void waitForAJAX() {
        final JavascriptExecutor js = context.getJavascriptExecutor();
        context.getWaitForElement().until(x -> js.executeScript("return jQuery.active == 0").equals(true));
    }

    protected void waitForPageToLoad() {
        context.getWaitForElement()
                .until(x -> (context.getJavascriptExecutor()).executeScript("return document.readyState").equals("complete"));
    }

    public void waitForDynamicChanges() {
        context.getWaiting().doWait();
    }

    protected <V> void waitOptionalWithTimeout(final Function<? super WebDriver, V> conditionIsTrue,
                                               final int timeoutInSeconds) {
        try {
            new WebDriverWait(context.getDriver(), timeoutInSeconds).until(conditionIsTrue);
        } catch (final TimeoutException e) {
            LOGGER.warn("waitOptionalWithTimeout: ", e);
        }
    }

    protected void performFailSafeAction(final Action action) {
        try {
            action.perform();
        } catch (final Exception e) {
            LOGGER.warn("performFailSafeAction: ", e);
        }
    }

    protected void selectDropDownByText(final WebElement webElement, final String value) {
        final Select select = new Select(webElement);
        select.selectByVisibleText(value);
        waitForDynamicChanges();
    }

    protected void selectDropDownByValue(final WebElement webElement, final String value) {
        final Select select = new Select(webElement);
        select.selectByValue(value);
        waitForDynamicChanges();
    }

    protected void clearInputAndSendKeys(final WebElement input, final String text) {
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(text);
        waitForDynamicChanges();
    }

    protected void performJSClick(final WebElement webElement) {
        final JavascriptExecutor executor = (JavascriptExecutor) context.getDriver();
        executor.executeScript("arguments[0].click();", webElement);
        waitForDynamicChanges();
    }

    protected void setCheckboxSelection(final WebElement checkbox, final boolean value) {
        if (checkbox.isSelected() != value) {
            clickAndWaitForLoading(checkbox);
        }
    }

    protected void clickAndWaitForLoading(final WebElement element) {
        element.click();
        waitForDynamicChanges();
    }

    protected void fillInputOptionally(final Supplier<WebElement> element, final String text) {
        if (text != null) {
            clearInputAndSendKeys(element.get(), text);
        }
    }

    protected boolean selectDropdownOptionally(final Supplier<WebElement> select, final String text) {
        if (text != null) {
            selectDropDownByText(select.get(), text);
        }
        return text != null;
    }

    protected T instantiatePage(final Class<T> pageClass) {
        return PageUtil.instantiatePage(pageClass);
    }

}
