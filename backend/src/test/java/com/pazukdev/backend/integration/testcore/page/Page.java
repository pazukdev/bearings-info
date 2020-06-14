package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface Page {

    String getUrl();

    WebElement getControlElement();

    Page initElements(final TestContext context);

}
