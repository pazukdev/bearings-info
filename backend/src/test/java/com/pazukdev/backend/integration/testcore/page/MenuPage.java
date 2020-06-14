package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.constant.Constant;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MenuPage extends AbstractPage {

    @FindBy(id = Constant.ElementId.ADDITIONAL_MENU)
    protected WebElement menu;

    @Override
    public String getUrl() {
        return getURL("menu");
    }

    @Override
    public WebElement getControlElement() {
        return menu;
    }

}
