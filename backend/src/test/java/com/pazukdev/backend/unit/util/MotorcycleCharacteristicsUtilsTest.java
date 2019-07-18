package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.util.FuelUtil;
import com.pazukdev.backend.util.SpeedUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MotorcycleCharacteristicsUtilsTest {

    @Test
    public void operationalRangeTest() {
        Assert.assertEquals(100, FuelUtil.getOperationalRange(10.0, 10.0).intValue());
    }

    @Test
    public void speedTest() {
        Assert.assertEquals(75, SpeedUtil.calculateSpeed(5000).intValue());
    }

}
