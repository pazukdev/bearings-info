package com.pazukdev.backend.integration.testcore.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MotorcyclePage extends AbstractPage {

    private final static String URL = "http://localhost:8091/bearings-info/api/motorcycle/list";

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





















