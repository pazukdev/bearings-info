package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;

/**
 * @author Siarhei Sviarkaltsau
 */
public class GetPageAction extends AbstractAction {

    private final Page page;

    public GetPageAction(final TestContext context, final Page page) {
        super(context);
        this.page = page;
    }

    @Override
    public void perform() {
        getPage(context, page);
    }

    private void getPage(final TestContext context, final Page page) {
        final String url = page.getURL();
        context.getDriver().get(url);
    }

}
