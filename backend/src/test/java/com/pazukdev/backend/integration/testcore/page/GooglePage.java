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
public class GooglePage extends AbstractPage {

    private final static String URL = "https://www.google.com/";

    @FindBy(id = "hplogo")
    private WebElement googleLogo;

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public WebElement getControlElement() {
        return googleLogo;
    }

}
