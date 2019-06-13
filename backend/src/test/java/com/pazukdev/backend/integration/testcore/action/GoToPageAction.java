package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.route.RouteNode;
import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import com.pazukdev.backend.integration.testcore.route.Route;
import org.openqa.selenium.WebElement;

/**
 * @author Siarhei Sviarkaltsau
 */
public class GoToPageAction<Destination extends Page> extends AbstractAction {

    private final Route route;

    public GoToPageAction(TestContext context, final Route route) {
        super(context);
        this.route = route;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Destination perform() {
        clickAll();
        return (Destination) route.getDestination();
    }

    private void clickAll() {
        for (final RouteNode routeNode : route.getNodes()) {
            routeNode.getPage().initElements(context);
            for (final WebElement element : routeNode.getElementsToClick()) {
                clickAndWaitForLoading(element);
            }
        }
    }

}
