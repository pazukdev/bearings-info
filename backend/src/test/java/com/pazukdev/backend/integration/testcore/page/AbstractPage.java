package com.pazukdev.backend.integration.testcore.page;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class AbstractPage implements Page {

    public void initElements(final TestContext context) {
        PageFactory.initElements(context.getDriver(), this);
    }

}
