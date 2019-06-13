package com.pazukdev.backend.integration.testcore.route;

import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class Route {

    private final List<RouteNode> nodes;

    public Page getDestination() {
        return nodes.get(nodes.size() - 1).getPage();
    }

}
