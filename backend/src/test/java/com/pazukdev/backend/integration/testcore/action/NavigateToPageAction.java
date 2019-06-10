package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class NavigateToPageAction<PageClass> implements Action {

    private final TestContext context;
    private final Page page;

    @Override
    public void perform() {
        getPage(context, page);
    }

    private void getPage(final TestContext context, final Page page) {
        context.getDriver().get(page.getURL());
    }

}
