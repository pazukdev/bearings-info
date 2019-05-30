package com.pazukdev.backend.tablemodel;

import com.pazukdev.backend.characteristic.Characteristic;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.pazukdev.backend.util.SpecificStringUtil.extractIntegerAutomatically;
import static com.pazukdev.backend.util.SpecificStringUtil.getStringBeforeParentheses;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TableRow {

    private final Map<String, String> data;

    public static TableRow create() {
        return new TableRow(new HashMap<>());
    }

    public void put(final String key, final String value) {
        data.put(key, value);
    }

    public String getStringValue(final Characteristic characteristic) {
        return data.get(characteristic.toString().toLowerCase());
    }

    public String getStringValueBeforeParenthesises(final Characteristic characteristic) {
        return getStringBeforeParentheses(getStringValue(characteristic));
    }

    public Integer getIntegerValue(final Characteristic characteristic) {
        return extractIntegerAutomatically(getStringValue(characteristic));
    }

}
