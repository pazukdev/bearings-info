package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.item.NestedItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PartsTable extends AbstractDto {

    private List<NestedItemDto> parts = new ArrayList<>();
    private List<PartsTable> tables = new ArrayList<>();

    public static PartsTable create(final List<NestedItemDto> nestedItems, final Set<String> partCategories) {
        final PartsTable partsTable = new PartsTable();
        partsTable.setName("Parts");
        for (final String category : partCategories) {
            final PartsTable categoryTable = new PartsTable();
            categoryTable.setName(category);
            partsTable.getTables().add(categoryTable);
        }
        for (final NestedItemDto nestedItem : nestedItems) {
            addToCategoryTable(partsTable.getTables(), nestedItem);
        }
        return partsTable;
    }

    public static void addToCategoryTable(final List<PartsTable> categoryTables,
                                          final NestedItemDto nestedItem) {
        for (final PartsTable categoryTable : categoryTables) {
            if (categoryTable.getName().equals(nestedItem.getItemCategory())) {
                categoryTable.getParts().add(nestedItem);
            }
        }
    }

    public static PartsTable createSingleCategoryTable(final List<NestedItemDto> categorizedItems) {
        final PartsTable partsTable = new PartsTable();
        partsTable.setName(categorizedItems.get(0).getItemCategory());
        partsTable.getParts().addAll(categorizedItems);
        return partsTable;
    }

}