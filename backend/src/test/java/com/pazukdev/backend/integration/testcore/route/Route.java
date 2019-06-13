package com.pazukdev.backend.integration.testcore.route;

import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@RequiredArgsConstructor
public class Route<Destination extends Page> {

    private final List<RouteNode> nodes;

    @SuppressWarnings("unchecked")
    public Class<Destination> getDestination() {
        return nodes.get(nodes.size() - 1).getPageClass();
    }

    public Route(final RouteNode... nodes) {
        this(Arrays.asList(nodes));
    }

}
