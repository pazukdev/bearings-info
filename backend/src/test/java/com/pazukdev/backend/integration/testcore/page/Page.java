package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface Page {

    String getURL();

    WebElement getControlElement();

    void initElements(TestContext context);

}
