package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import lombok.Data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class GooglePage implements Page {

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

    @Override
    public void initElements(final TestContext context) {
        PageFactory.initElements(context.getDriver(), this);
    }

}
