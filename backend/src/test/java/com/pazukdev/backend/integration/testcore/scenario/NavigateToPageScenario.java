package com.pazukdev.backend.integration.testcore.scenario;

import com.pazukdev.backend.integration.testcore.action.Action;
import com.pazukdev.backend.integration.testcore.action.NavigateToPageAction;
import com.pazukdev.backend.integration.testcore.asserter.Asserter;
import com.pazukdev.backend.integration.testcore.asserter.PageLoadAsserter;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class NavigateToPageScenario implements Scenario {

    private final TestContext context;
    private final Page page;

    @Override
    public void perform() {
        final Action navigateToPage = new NavigateToPageAction(context, page);
        final Asserter pageLoadAsserter = new PageLoadAsserter(context, page);

        navigateToPage.perform();
        pageLoadAsserter.perform();
    }

}
