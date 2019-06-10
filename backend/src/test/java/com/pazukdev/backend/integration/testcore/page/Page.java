package com.pazukdev.backend.integration.testcore.page;

import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public interface Page {

    String getURL();

    WebElement getControlElement();

}
