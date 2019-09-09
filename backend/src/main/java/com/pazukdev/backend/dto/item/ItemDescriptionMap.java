package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.entity.item.ItemEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class ItemDescriptionMap {

    private ItemEntity parent;
    private Map<String, String> characteristics = new HashMap<>();
    private Map<String, String> selectableCharacteristics = new HashMap<>();
    private Map<String, String> items = new HashMap<>();

}
