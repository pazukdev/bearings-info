package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.entity.item.ItemEntity;
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
        final List<List<ItemEntity>> categories = ItemUtil.categorize(mockData.itemsList());
        Assert.assertTrue(categories.size() == 3);
    }

}
