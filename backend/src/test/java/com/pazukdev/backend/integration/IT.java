package com.pazukdev.backend.integration;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.GooglePage;
import com.pazukdev.backend.integration.testcore.scenario.NavigateToPageScenario;
import com.pazukdev.backend.integration.testcore.scenario.Scenario;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        new NavigateToPageScenario<>(context, GooglePage.class).perform();
    }

}
