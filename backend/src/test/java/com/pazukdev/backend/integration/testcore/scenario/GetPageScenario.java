package com.pazukdev.backend.integration.testcore.scenario;

import com.pazukdev.backend.integration.testcore.action.GetPageAction;
import com.pazukdev.backend.integration.testcore.asserter.PageLoadAsserter;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import com.pazukdev.backend.integration.testcore.route.Route;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@RequiredArgsConstructor
public class GetPageScenario<Destination extends Page> implements Scenario<Destination> {

    private final TestContext context;
    private final Class<Destination> destinationPageClass;
    private final Route<Destination> routeToDestination;

    public GetPageScenario(final TestContext context, final Class<Destination> destinationPageClass) {
        this(context, destinationPageClass, null);
    }

    public GetPageScenario(final TestContext context, final Route<Destination> routeToDestination) {
        this(context, null, routeToDestination);

    }

    @Override
    public Destination perform() {
        final Destination page = Objects.requireNonNull(getPageAction()).perform();
        new PageLoadAsserter(context, page).perform();
        return page;
    }

    private GetPageAction<Destination> getPageAction() {

        if (destinationPageClass != null) {
            return new GetPageAction<>(context, destinationPageClass);
        }

        if (routeToDestination != null) {
            return new GetPageAction<>(context, routeToDestination);
        }

        return null;
    }

}
