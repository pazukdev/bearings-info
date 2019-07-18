package com.pazukdev.backend.entity.product.unit.engine;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.bearing.BearingFactory;
import com.pazukdev.backend.entity.product.oil.Oil;
import com.pazukdev.backend.entity.product.oil.OilFactory;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlug;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugFactory;
import com.pazukdev.backend.entity.product.unit.UnitFactory;
import com.pazukdev.backend.service.BearingService;
import com.pazukdev.backend.service.SparkPlugService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class EngineFactory extends UnitFactory<Engine> {

    private final BearingFactory bearingFactory;
    private final OilFactory oilFactory;
    private final SparkPlugFactory sparkPlugFactory;

    public EngineFactory(final ServiceContext context,
                         final ManufacturerFactory manufacturerFactory,
                         final BearingFactory bearingFactory,
                         final OilFactory oilFactory,
                         final SparkPlugFactory sparkPlugFactory) {
        super(context, manufacturerFactory);
        this.bearingFactory = bearingFactory;
        this.oilFactory = oilFactory;
        this.sparkPlugFactory = sparkPlugFactory;
    }

    @Override
    public Engine createEntity() {
        return new Engine();
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.ENGINE_FILE_NAME);
    }

    @Override
    protected void applyCharacteristics(final Engine engine, final TableRow tableRow) {
        super.applyCharacteristics(engine, tableRow);

        applyPower(engine, tableRow);
        applyTorque(engine, tableRow);
        applySpeed(engine, tableRow);
        applySparkPlug(engine, tableRow);
        applyBearings(engine, tableRow);
        applyOils(engine, tableRow);
    }

    private void applyPower(final Engine engine, final TableRow tableRow) {
        engine.setPowerHp(tableRow.getIntegerValue(Characteristic.POWER_HP));
    }

    private void applyTorque(final Engine engine, final TableRow tableRow) {
        engine.setTorqueNm(tableRow.getIntegerValue(Characteristic.TORQUE_NM));
    }

    private void applySpeed(final Engine engine, final TableRow tableRow) {
        engine.setSpeedRpm(tableRow.getIntegerValue(Characteristic.SPEED_RPM));
    }

    private void applySparkPlug(final Engine engine, final TableRow tableRow) {
        final String sparkPlugName = tableRow.getStringValue(Characteristic.SPARK_PLUG);
        final SparkPlugService sparkPlugService = context != null ? context.getSparkPlugService() : null;
        final SparkPlug sparkPlug = getEntity(sparkPlugName, sparkPlugService, sparkPlugFactory);

        engine.setSparkPlug(sparkPlug);
    }

    private void applyBearings(final Engine engine, final TableRow tableRow) {
        final List<String> bearingNames = tableRow.getStringValues(Characteristic.BEARING);
        final BearingService bearingService = context != null ? context.getBearingService() : null;
        for (final String bearingName : bearingNames) {
            engine.getBearings().add(getEntity(bearingName, bearingService, bearingFactory));
        }
    }

    private void applyOils(final Engine engine, final TableRow tableRow) {
        if (context == null || context.getOilService() == null) {
            return;
        }
        for (final Oil oil : context.getOilService().findAll()) {
            engine.getOils().add(oil);
        }
    }

}




















