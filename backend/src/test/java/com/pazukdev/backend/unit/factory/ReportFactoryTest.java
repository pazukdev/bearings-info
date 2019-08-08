package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.report.FuelReport;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReport;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ReportFactoryTest {

    private final MotorcycleEntity motorcycle = new MockData().motorcycle();

    @Test
    public void speedReportTest() {
        final SpeedReport report = ReportFactory.createSpeedReport(motorcycle);

        assertEquals(92, report.getMaxSpeedKmh().intValue());
    }

    @Test
    public void fuelReportTest() {
        final FuelReport report = ReportFactory.createFuelReport(motorcycle);

        assertEquals(190, report.getOperationalRangeKm().intValue());
    }



}
