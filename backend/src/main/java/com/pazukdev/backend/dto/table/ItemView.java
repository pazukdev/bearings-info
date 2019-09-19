package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.item.ItemSelect;
import lombok.Data;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class ItemView {

    private TableDto header;
    private TableViewDto items;
    private TableDto replacers;
    private Long itemId;
    private List<ItemSelect> allItems;
    private List<ItemSelect> sameCategoryItems;

}
