package com.pazukdev.backend.integration;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.GooglePage;
import com.pazukdev.backend.integration.testcore.page.MainPage;
import com.pazukdev.backend.integration.testcore.route.Route;
import com.pazukdev.backend.integration.testcore.route.RouteNode;
import com.pazukdev.backend.integration.testcore.scenario.GetPageScenario;
import com.pazukdev.backend.integration.testcore.util.TestContextUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(SpringRunner.class)
public class IT {

    private TestContext context;

    @Before
    public void createContext() {
        this.context = TestContextUtil.createTestContext();
    }

    @Test
    public void basicWebdriverAndBrowserTest() {
        new GetPageScenario<>(context, GooglePage.class).perform();
    }

    @Test
    public void getMainPageTest() {
        new GetPageScenario<>(context, MainPage.class).perform();
    }

    @Test
    public void getMotorcyclePageTest() {
        final RouteNode<MainPage> mainPage = new RouteNode<>(MainPage.class, "motorcyclesButton");
        new GetPageScenario<>(context, new Route<>(mainPage)).perform();
    }

    @Test
    public void getBearingPageTest() {
        final RouteNode<MainPage> mainPage = new RouteNode<>(MainPage.class, "bearingsButton");
        new GetPageScenario<>(context, new Route<>(mainPage)).perform();
    }

    @Test
    public void getSealPageTest() {
        final RouteNode<MainPage> mainPage = new RouteNode<>(MainPage.class, "sealsButton");
        new GetPageScenario<>(context, new Route<>(mainPage)).perform();
    }

}

























