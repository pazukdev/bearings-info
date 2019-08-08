package com.pazukdev.backend.dto.report;

import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.util.FuelUtil;
import com.pazukdev.backend.util.SpeedUtil;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ReportFactory {

    public static FuelReport createFuelReport(final MotorcycleEntity motorcycle) {
        final double fuelCapacityL = motorcycle.getFuelCapacityL();
        final double fuelConsumptionLPer100Km = 10D;

        final FuelReport report = new FuelReport();
        report.setOperationalRangeKm(FuelUtil.getOperationalRange(fuelCapacityL, fuelConsumptionLPer100Km));
        return report;
    }

    public static SpeedReport createSpeedReport(final MotorcycleEntity motorcycle) {
        final int engineSpeedRpm = motorcycle.getEngine().getSpeedRpm();

        final SpeedReport report = new SpeedReport();
        report.setMaxSpeedKmh(SpeedUtil.calculateSpeed(engineSpeedRpm));
        return report;
    }

}
