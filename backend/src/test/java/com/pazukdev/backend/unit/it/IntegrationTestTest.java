package com.pazukdev.backend.unit.it;

import com.pazukdev.backend.integration.testcore.core.TestContextImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class IntegrationTestTest {

    @InjectMocks
    private TestContextImpl context;

    @Ignore
    @Test
    public void testContextTest() {
        final WebDriver driver1 = context.getDriver();
        final WebDriver driver2 = context.getDriver();

        Assert.assertNotNull(driver1);
        Assert.assertSame(driver1, driver2);
    }

}
