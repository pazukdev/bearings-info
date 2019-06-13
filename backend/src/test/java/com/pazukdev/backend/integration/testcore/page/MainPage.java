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
public class MainPage extends AbstractPage {

    private final static String URL = "https://www.google.com/";

    @FindBy(xpath = "")
    private WebElement pageHeader;
    @FindBy(xpath = "")
    private WebElement motorcycleButton;
    @FindBy(xpath = "")
    private WebElement bearingButton;
    @FindBy(xpath = "")
    private WebElement sealButton;

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public WebElement getControlElement() {
        return pageHeader;
    }

}
