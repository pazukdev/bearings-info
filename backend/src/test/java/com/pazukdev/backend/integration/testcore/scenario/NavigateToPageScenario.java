package com.pazukdev.backend.integration.testcore.scenario;

import com.pazukdev.backend.integration.testcore.action.GetPageAction;
import com.pazukdev.backend.integration.testcore.asserter.PageLoadAsserter;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class NavigateToPageScenario<T extends Page> implements Scenario<T> {

    private final TestContext context;
    private final Class<T> destination;

    @Override
    public T perform() {
        final T page = new GetPageAction<>(context, destination).perform();
        new PageLoadAsserter(context, page).perform();
        return page;
    }

}
