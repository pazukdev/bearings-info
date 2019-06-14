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

    private final static String URL = "http://localhost:8091/bearings-info/api/";

    @FindBy(id = "appName")
    private WebElement appName;
    @FindBy(id = "motorcyclesButton")
    private WebElement motorcyclesButton;
    @FindBy(id = "bearingsButton")
    private WebElement bearingsButton;
    @FindBy(id = "sealsButton")
    private WebElement sealsButton;

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public WebElement getControlElement() {
        return appName;
    }

}
