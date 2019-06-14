package com.pazukdev.backend.integration.testcore.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SealsPage extends AbstractPage {

    private final static String URL = "http://localhost:8091/bearings-info/api/seal/list";

    @FindBy(xpath = "")
    private WebElement table;

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public WebElement getControlElement() {
        return pageName;
    }

}
