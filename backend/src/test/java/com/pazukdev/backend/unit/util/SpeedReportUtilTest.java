package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.dto.MotorcycleSpeedReportRS;
import com.pazukdev.backend.entity.product.motorcycle.Motorcycle;
import com.pazukdev.backend.entity.product.unit.engine.Engine;
import org.junit.Test;

import static com.pazukdev.backend.util.SpeedReportUtil.createSpeedReport;
import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SpeedReportUtilTest {

    @Test
    public void test() {
        final int engineSpeedRpm = 5000;
        final int expectedMotorcycleSpeedKmh = 75;

        final Motorcycle motorcycle = new Motorcycle();
        final Engine engine = new Engine();
        engine.setSpeedRpm(engineSpeedRpm);
        motorcycle.setEngine(engine);

        final MotorcycleSpeedReportRS report = createSpeedReport(motorcycle);

        assertEquals(expectedMotorcycleSpeedKmh, report.getMaxSpeedKmh().intValue());
    }

}
