package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.item.NestedItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemView extends AbstractDto {

    private boolean searchEnabled = true;
    private boolean specialItemView = false;
    private boolean newItem = false;
    private String category;
    private TableDto header;
    private TableViewDto items;
    private PartsTable partsTable;
    private ReplacersTable replacersTable;
    private Long itemId;
//    private List<ItemSelect> allItems = new ArrayList<>();
//    private List<ItemSelect> sameCategoryItems = new ArrayList<>();
    private List<NestedItemDto> possibleParts = new ArrayList<>();
    private List<NestedItemDto> replacers = new ArrayList<>();

}
