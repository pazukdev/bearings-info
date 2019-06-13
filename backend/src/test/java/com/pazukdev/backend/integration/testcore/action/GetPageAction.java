package com.pazukdev.backend.integration.testcore.action;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;

import java.util.Objects;

/**
 * @author Siarhei Sviarkaltsau
 */
public class GetPageAction<T extends Page> extends AbstractAction<T> {

    private final Class<T> destination;

    public GetPageAction(final TestContext context, Class<T> destination) {
        super(context);
        this.destination = destination;
    }

    @Override
    public T perform() {
        return getPage(context, destination);
    }

    private T getPage(final TestContext context, final Class<T> destination) {
        T page = null;

        try {
            page = destination.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String url = Objects.requireNonNull(page).getURL();
        context.getDriver().get(url);

        return page;
    }

}
