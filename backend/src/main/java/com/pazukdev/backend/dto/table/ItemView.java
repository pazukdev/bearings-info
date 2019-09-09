package com.pazukdev.backend.dto.table;

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

}
