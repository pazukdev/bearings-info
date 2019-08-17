package com.pazukdev.backend.unit.item;

import com.pazukdev.backend.dto.item.ItemDescriptionDto;
import com.pazukdev.backend.dto.item.ItemDescriptionDtoFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemTest {

    @Test
    public void itemDescriptionFactoryTest() {
        final String description = "Operational range, km: 200; Max speed, km/h: 100";
        final List<ItemDescriptionDto> descriptions = ItemDescriptionDtoFactory.create(description);
        assertEquals("Operational range, km", descriptions.get(0).getParameter());
        assertEquals("Max speed, km/h", descriptions.get(1).getParameter());
        assertEquals("200", descriptions.get(0).getValue());
        assertEquals("100", descriptions.get(1).getValue());
    }

}
