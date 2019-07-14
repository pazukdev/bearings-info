package com.pazukdev.backend.unit.converter.util;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.product.AbstractProductDto;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.product.AbstractProduct;
import com.pazukdev.backend.entity.product.Bearing;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTestUtil {

    public static void validateBearingSetConversion(final Set<Bearing> entities, final Set<Long> bearingIds) {
        assertNotNull(entities);
        assertNotNull(bearingIds);

        final Set<Long> entitiesBearingIds= entities.stream().map(Bearing::getId).collect(Collectors.toSet());
        assertTrue(entitiesBearingIds.size() > 0);
        assertTrue(entitiesBearingIds.size() == bearingIds.size());

        for (final Long bearingId : bearingIds) {
            assertTrue(entitiesBearingIds.contains(bearingId));
        }
    }

    public static void validateBearingConversion(final Bearing entity, final BearingDto dto) {
        validateAbstractProductConversion(entity, dto);
        assertEquals(entity.getType(), dto.getType());
        assertEquals(entity.getRollingElement(), dto.getRollingElement());
        assertEquals(entity.getRollingElementsQuantity(), dto.getRollingElementsQuantity());
    }

    public static void validateAbstractProductConversion(final AbstractProduct entity, final AbstractProductDto dto) {
        validateAbstractEntityConversion(entity, dto);
        assertEquals(entity.getProductionStartYear(), dto.getProductionStartYear());
        assertEquals(entity.getProductionStopYear(), dto.getProductionStopYear());
        if (entity.getManufacturer() == null) {
            assertNull(dto.getManufacturerId());
        } else {
            assertEquals(entity.getManufacturer().getId(), dto.getManufacturerId());
        }
    }

    public static void validateAbstractEntityConversion(final AbstractEntity entity, final AbstractDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

}
