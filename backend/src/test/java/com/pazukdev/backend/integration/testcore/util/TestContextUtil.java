package com.pazukdev.backend.integration.testcore.util;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.core.TestContextImpl;

/**
 * @author Siarhei Sviarkaltsau
 */
public class TestContextUtil {

    public static TestContext createTestContext() {
        return new TestContextImpl();
    }

}
