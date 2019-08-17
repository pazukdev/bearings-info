package com.pazukdev.backend.dto.item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemDescriptionDtoFactory {

    public static List<ItemDescriptionDto> create(final String description) {
        final List<ItemDescriptionDto> descriptions = new ArrayList<>();
        final String[] lines = description.split("; ");
        for (final String line : lines) {
            final ItemDescriptionDto descriptionDto = new ItemDescriptionDto();
            descriptionDto.setParameter(line.split(": ")[0]);
            descriptionDto.setValue(line.split(": ")[1]);
            descriptions.add(descriptionDto);
        }
        return descriptions;
    }

}
