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
public class AppMainPage implements Page {

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

    @Override
    public void initElements(final TestContext context) {
        PageFactory.initElements(context.getDriver(), this);
    }

}
