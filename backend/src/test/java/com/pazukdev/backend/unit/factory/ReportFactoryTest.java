package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.dto.report.FuelReportRS;
import com.pazukdev.backend.dto.report.ReportFactory;
import com.pazukdev.backend.dto.report.SpeedReportRS;
import com.pazukdev.backend.entity.product.motorcycle.Motorcycle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ReportFactoryTest {

    private final Motorcycle motorcycle = new MockData().motorcycle();

    @Test
    public void speedReportTest() {
        final SpeedReportRS report = ReportFactory.createSpeedReport(motorcycle);

        assertEquals(92, report.getMaxSpeedKmh().intValue());
    }

    @Test
    public void fuelReportTest() {
        final FuelReportRS report = ReportFactory.createFuelReport(motorcycle);

        assertEquals(190, report.getOperationalRangeKm().intValue());
    }



}
