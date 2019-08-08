package com.pazukdev.backend.dto.item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
public class ItemDescriptionFactory {

    public static List<ItemDescription> create(final String description) {
        final List<ItemDescription> descriptions = new ArrayList<>();
        final String[] lines = description.split("; ");
        for (final String line : lines) {
            descriptions.add(new ItemDescription(line.split(": ")[0], line.split(": ")[1]));
        }
        return descriptions;
    }

}
