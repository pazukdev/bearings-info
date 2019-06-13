package com.pazukdev.backend.integration.testcore.route;

import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class RouteNode {

    private final Page page;
    private final List<WebElement> elementsToClick;

}
