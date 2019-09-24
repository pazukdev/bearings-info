package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.item.ChildItemDto;
import com.pazukdev.backend.dto.item.ItemSelect;
import com.pazukdev.backend.dto.item.ReplacerDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class ItemView {

    private TableDto header;
    private TableViewDto items;
    private ChildItemsTable childItemsTable;
    private ReplacersTable replacersTable;
    private Long itemId;
    private List<ItemSelect> allItems = new ArrayList<>();
    private List<ItemSelect> sameCategoryItems = new ArrayList<>();
    private List<ChildItemDto> possibleParts = new ArrayList<>();
    private List<ReplacerDto> replacers = new ArrayList<>();

}
