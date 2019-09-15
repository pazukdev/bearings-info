package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.entity.item.TransitiveItem;
import com.pazukdev.backend.util.ItemUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemUtilTest {

    final MockData mockData = new MockData();

    @Test
    public void categorizeTest() {
        final List<List<TransitiveItem>> categories = ItemUtil.categorize(mockData.itemsList());
        Assert.assertTrue(categories.size() == 3);
    }

    @Test
    public void getValueFromDescription() {
        Assert.assertEquals("{name=Bob, weight=200}", ItemUtil.toMap(description()).toString());
        Assert.assertEquals("Bob", ItemUtil.getValueFromDescription(description(), "name"));
    }

    private String description() {
        return "name:Bob;weight:200;";
    }

}
