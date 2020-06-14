package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.constant.Constant;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Siarhei Sviarkaltsau
 */
@NoArgsConstructor
public class ItemPage extends AbstractPage {

    private String itemId;

    @FindBy(id = Constant.ElementId.ITEM_LOCALIZED_NAME)
    private WebElement name;

    public ItemPage(final String itemId) {
        this.itemId = itemId.replaceAll(" ", "_");
    }

    @Override
    public String getUrl() {
        return getURL("item/id/" + itemId);
    }

    @Override
    public WebElement getControlElement() {
        return name;
    }

}
