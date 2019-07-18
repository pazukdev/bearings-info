package com.pazukdev.backend.unit.util;

import org.junit.Test;

import static com.pazukdev.backend.util.SpeedCalculatorUtil.calculateSpeed;
import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SpeedCalculatorUtilTest {

    @Test
    public void test() {
        final int engineSpeedRpm = 5000;
        final int expectedMotorcycleSpeedKmh = 75;
        assertEquals(expectedMotorcycleSpeedKmh, calculateSpeed(engineSpeedRpm));
    }

}
