package com.pazukdev.backend.integration;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.GooglePage;
import com.pazukdev.backend.integration.testcore.page.MainPage;
import com.pazukdev.backend.integration.testcore.route.Route;
import com.pazukdev.backend.integration.testcore.route.RouteNode;
import com.pazukdev.backend.integration.testcore.scenario.GetPageScenario;
import com.pazukdev.backend.integration.testcore.util.PageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IT {

    @Autowired
    private TestContext context;

    @Test
    public void basicWebdriverAndBrowserTest() {
        new GetPageScenario<>(context, GooglePage.class).perform();
    }

    @Test
    public void getMainPageTest() {
        new GetPageScenario<>(context, MainPage.class).perform();
    }

    @Test
    public void getMotorcyclesPageTest() {
        final RouteNode<MainPage> mainPage = new RouteNode<>(MainPage.class, "motorcyclesButton");
        new GetPageScenario<>(context, new Route<>(mainPage)).perform();
    }

    @Test
    public void getSealsPageTest() {
        final RouteNode<MainPage> mainPage = new RouteNode<>(MainPage.class, "sealsButton");
        new GetPageScenario<>(context, new Route<>(mainPage)).perform();
    }

}

























