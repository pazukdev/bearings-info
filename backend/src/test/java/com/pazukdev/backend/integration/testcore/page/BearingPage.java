package com.pazukdev.backend.integration.testcore.page;

import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public class BearingPage extends AbstractPage {

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public WebElement getControlElement() {
        return title;
    }

}