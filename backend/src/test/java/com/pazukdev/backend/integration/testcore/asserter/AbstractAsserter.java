package com.pazukdev.backend.integration.testcore.asserter;

import com.pazukdev.backend.integration.testcore.core.TestContext;
import com.pazukdev.backend.integration.testcore.page.Page;
import lombok.Data;
import org.junit.Assert;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public abstract class AbstractAsserter<EntityType, ConcretePage extends Page> implements Asserter {

    protected final TestContext context;
    protected EntityType expected;
    protected ConcretePage page;

    protected EntityType createSimplifiedExpected(EntityType expected) {
        return expected;
    }

    protected EntityType createActual(ConcretePage source) {
        return null;
    }

    protected void assertEquals(final EntityType simplifiedExpected, final EntityType actual) {
        Assert.assertEquals(simplifiedExpected, actual);
    }

}
