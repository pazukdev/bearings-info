package com.pazukdev.backend.integration.testcore.route;

import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class RouteNode<T extends Page> {

    private final Class<T> pageClass;
    private final List<WebElement> elementsToClick;

    public RouteNode(final Class<T> pageClass, final WebElement... elements) {
        this.pageClass = pageClass;
        this.elementsToClick = Arrays.asList(elements);
    }

}
