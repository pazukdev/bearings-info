package com.pazukdev.backend.entity.product.motorcycle;

import com.pazukdev.backend.characteristic.Specification;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.ProductFactory;
import com.pazukdev.backend.entity.product.unit.engine.Engine;
import com.pazukdev.backend.entity.product.unit.engine.EngineFactory;
import com.pazukdev.backend.service.EngineService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import com.pazukdev.backend.util.WeightUtil;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class MotorcycleFactory extends ProductFactory<Motorcycle> {

    private final EngineFactory engineFactory;

    public MotorcycleFactory(final ServiceContext context,
                             final ManufacturerFactory manufacturerFactory,
                             final EngineFactory engineFactory) {
        super(context, manufacturerFactory);
        this.engineFactory = engineFactory;
    }

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.file(CSVFileUtil.MOTORCYCLE_FILE_NAME);
    }

    @Override
    public Motorcycle createEntity() {
        return new Motorcycle();
    }

    @Override
    protected void applyCharacteristics(final Motorcycle motorcycle, final TableRow tableRow) {
        super.applyCharacteristics(motorcycle, tableRow);

        applyWeight(motorcycle, tableRow);
        applyEngine(motorcycle, tableRow);
        applyFuelCapacity(motorcycle, tableRow);
    }

    private void applyEngine(final Motorcycle motorcycle, final TableRow tableRow) {
        final String engineName = tableRow.getStringValue(Specification.ENGINE);
        final EngineService engineService = context != null ? context.getEngineService() : null;
        final Engine engine = getEntity(engineName, engineService, engineFactory);

        motorcycle.setEngine(engine);
    }

    private void applyWeight(final Motorcycle motorcycle, final TableRow tableRow) {
        final Integer weight_kg = tableRow.getIntegerValue(Specification.WEIGHT_KG);
        motorcycle.setWeightG(WeightUtil.toG(weight_kg));
    }

    private void applyFuelCapacity(final Motorcycle motorcycle, final TableRow tableRow) {
        final double capacity = tableRow.getIntegerValue(Specification.FUEL_CAPACITY_L);
        motorcycle.setFuelCapacityL(capacity);
    }

}








