package com.pazukdev.backend.dto.table;

import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class ItemView {

    private TableDto header;
    private TableViewDto items;
    private TableDto replacers;

}
