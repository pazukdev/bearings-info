package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.item.NestedItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemView extends AbstractDto {

    private boolean searchEnabled;
    private boolean ordinaryItem;
    private boolean specialItemView;
    private boolean itemsManagement;
    private boolean newItem;
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
    private Set<String> categories = new HashSet<>();
    private Set<Long> idsToRemove = new HashSet<>();
    private Long creatorId;

}
