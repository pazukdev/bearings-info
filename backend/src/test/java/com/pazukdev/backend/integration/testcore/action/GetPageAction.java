package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import com.pazukdev.backend.integration.testcore.route.Route;
import com.pazukdev.backend.integration.testcore.route.RouteNode;
import lombok.Data;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class GetPageAction<Destination extends Page> extends AbstractAction<Destination> {

    private final Class<Destination> destination;
    private final Route<Destination> route;

    public GetPageAction(final TestContext context, Class<Destination> destination) {
        this(context, destination, null);
    }

    public GetPageAction(TestContext context, final Route<Destination> route) {
        this(context, null, route);
    }

    private GetPageAction(TestContext context, Class<Destination> destination, final Route<Destination> route) {
        super(context);
        this.route = route;
        this.destination = destination;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Destination perform() {

        if (destination != null) {
            return getPage(context, destination);
        }

        if (route != null) {
            clickAll();
            return instantiatePage(route.getDestination());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void clickAll() {
        for (final RouteNode routeNode : route.getNodes()) {
            instantiatePage(routeNode.getPageClass()).initElements(context);
            for (final WebElement element : (List<WebElement>) routeNode.getElementsToClick()) {
                clickAndWaitForLoading(element);
            }
        }
    }

    private Destination getPage(final TestContext context, final Class<Destination> destination) {
        final Destination page = instantiatePage(destination);
        final String url = Objects.requireNonNull(page).getURL();
        context.getDriver().get(url);
        return page;
    }

}
