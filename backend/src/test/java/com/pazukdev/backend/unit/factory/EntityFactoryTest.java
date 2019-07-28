package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.MockData;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.entity.manufacturer.ManufacturerEntity;
import com.pazukdev.backend.entity.manufacturer.ManufacturerFactory;
import com.pazukdev.backend.entity.product.bearing.BearingEntity;
import com.pazukdev.backend.entity.product.bearing.BearingFactory;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleEntity;
import com.pazukdev.backend.entity.product.motorcycle.MotorcycleFactory;
import com.pazukdev.backend.entity.product.oil.OilEntity;
import com.pazukdev.backend.entity.product.oil.OilFactory;
import com.pazukdev.backend.entity.product.seal.SealEntity;
import com.pazukdev.backend.entity.product.seal.SealFactory;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugEntity;
import com.pazukdev.backend.entity.product.sparkplug.SparkPlugFactory;
import com.pazukdev.backend.entity.product.unit.engine.EngineEntity;
import com.pazukdev.backend.entity.product.unit.engine.EngineFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static com.pazukdev.backend.util.CSVFileUtil.getFirstEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityFactoryTest {

    private final MockData mockData = new MockData();

    private final ServiceContext serviceContext = null;

    @Spy
    private ManufacturerFactory manufacturerFactory = new ManufacturerFactory();
    @Spy
    private BearingFactory bearingFactory = new BearingFactory(serviceContext, manufacturerFactory);
    @Spy
    private SealFactory sealFactory = new SealFactory(serviceContext, manufacturerFactory);
    @Spy
    private SparkPlugFactory sparkPlugFactory = new SparkPlugFactory(serviceContext, manufacturerFactory);
    @Spy
    private OilFactory oilFactory = new OilFactory(serviceContext,manufacturerFactory);
    @Spy
    private EngineFactory engineFactory = new EngineFactory(
            serviceContext, manufacturerFactory, bearingFactory, oilFactory, sparkPlugFactory);
    @InjectMocks
    private MotorcycleFactory motorcycleFactory;


    @Test
    public void manufacturerFactoryTest() {
        final ManufacturerEntity manufacturer = getFirstEntity(new ManufacturerFactory());

        assertEquals("imz", manufacturer.getName());
        assertEquals("1941", manufacturer.getFounded());
        assertNull(manufacturer.getDefunct());
    }

    @Test
    public void sparkPlugFactoryTest() {
        final SparkPlugEntity sparkPlug = getFirstEntity(sparkPlugFactory);

        assertEquals("a8u", sparkPlug.getName());
        assertEquals("10", sparkPlug.getHeatRange().toString());
    }

    @Test
    public void oilFactoryTest() {
        final OilEntity oil = getFirstEntity(oilFactory);

        assertEquals("10w-60", oil.getName());
        assertEquals("10w-60", oil.getViscosity());
        assertEquals("synthetic", oil.getBase());
        assertEquals("multigrade", oil.getSeasonality());
    }

    @Test
    public void engineFactoryTest() {
        final EngineEntity engine = getFirstEntity(engineFactory);

        assertEquals("mt-8", engine.getName());
        assertEquals("32", engine.getPowerHp().toString());
        assertEquals("40", engine.getTorqueNm().toString());
        assertEquals("5900", engine.getSpeedRpm().toString());
    }

    @Test
    public void motorcycleFactoryTest() {
        final MotorcycleEntity motorcycle = getFirstEntity(motorcycleFactory);

        assertEquals("m-72", motorcycle.getName());
        assertEquals("1941", motorcycle.getProductionStartYear().toString());
        assertEquals("1957", motorcycle.getProductionStopYear().toString());
        assertEquals("300000", motorcycle.getWeightG().toString());
        assertEquals("19.0", motorcycle.getFuelCapacityL().toString());
    }

    @Test
    public void bearingFactoryTest() {
        final BearingEntity bearing = getFirstEntity(bearingFactory);

        assertEquals("209", bearing.getName());
        assertNull(bearing.getProductionStartYear());
        assertNull(bearing.getProductionStopYear());
        assertEquals("deepgroove", bearing.getType());
        assertEquals("ball", bearing.getRollingElement());
        assertEquals("9", bearing.getRollingElementsQuantity().toString());
    }

    @Test
    public void sealFactoryTest() {
        final SealEntity seal = getFirstEntity(sealFactory);

        assertEquals("7201191", seal.getName());
        assertNull(seal.getProductionStartYear());
        assertNull(seal.getProductionStopYear());
        assertEquals("left", seal.getRotation());
        assertEquals("rubber", seal.getMaterial());
    }

}
