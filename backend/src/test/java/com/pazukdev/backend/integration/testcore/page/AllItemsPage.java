package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.constant.Constant;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
public class AllItemsPage extends AbstractPage {

    @FindBy(id = Constant.ElementId.VEHICLES_LIST)
    private WebElement vehiclesList;


    @Override
    public String getUrl() {
        return getURL("home");
    }

    @Override
    public WebElement getControlElement() {
        return vehiclesList;
    }

}
