package com.pazukdev.backend.unit.converter.util;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.entity.AbstractEntity;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ConverterTestUtil {

    public static void validateAbstractEntityConversion(final AbstractEntity entity, final AbstractDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
    }

}
