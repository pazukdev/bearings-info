package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.item.TransitiveItemDto;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class ItemView {

    private TableDto header;
    private TableDto selectableData;
    private TableViewDto items;
    private TableDto replacers;
    private TransitiveItemDto item;
    private Long itemId;

}
