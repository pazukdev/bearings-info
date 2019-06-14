package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class AbstractPage implements Page {

    @FindBy(id = "pageName")
    protected WebElement pageName;

    public void initElements(final TestContext context) {
        PageFactory.initElements(context.getDriver(), this);
    }

}
