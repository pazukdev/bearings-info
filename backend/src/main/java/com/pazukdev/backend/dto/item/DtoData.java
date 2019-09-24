package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.util.ItemUtil;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class DtoData {

    private String name;
    private Long itemId;
    private String itemName;
    private String manufacturer;
    private String buttonName;
    private String selectText;

    public DtoData(final Item item) {
        name = " - " + item.getName();
        itemId = item.getId();
        itemName = item.getName();
        buttonName = item.getName();
        manufacturer = ItemUtil.getValueFromDescription(item.getDescription(), "Manufacturer");
        selectText = item.getName();
        if (manufacturer != null) {
            selectText = manufacturer + " " + item.getName();
        }
        if (item.getCategory().equals("Seal")) {
            final String size = ItemUtil.getValueFromDescription(item.getDescription(), "Size, mm");
            selectText = size + " " + manufacturer + " " + item.getName();
        }
    }

}
