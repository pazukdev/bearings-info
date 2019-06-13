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
        MainPage page = new MainPage();
        page.getMotorcycleButton();
    }

}





















