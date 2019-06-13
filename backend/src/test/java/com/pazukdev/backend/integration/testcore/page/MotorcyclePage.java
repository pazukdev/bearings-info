package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MotorcyclePage implements Page {

    private final static String URL = "";

    @FindBy(xpath = "")
    private WebElement tableTitle;
    @FindBy(xpath = "")
    private WebElement table;

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public WebElement getControlElement() {
        return tableTitle;
    }

    public void getRoute() {
        AppMainPage page = new AppMainPage();
        page.getMotorcycleButton();
    }

    @Override
    public void initElements(final TestContext context) {
        PageFactory.initElements(context.getDriver(), this);
    }

}





















