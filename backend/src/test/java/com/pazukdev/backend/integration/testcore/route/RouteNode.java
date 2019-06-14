package com.pazukdev.backend.integration.testcore.route;

import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class RouteNode<T extends Page> {

    private final Class<T> pageClass;
    private final List<String> idsOfElementsToClick;

    public RouteNode(final Class<T> pageClass, final String... idsOfElementsToClick) {
        this.pageClass = pageClass;
        this.idsOfElementsToClick = Arrays.asList(idsOfElementsToClick);
    }

}
