package com.pazukdev.backend.tablemodel;

import com.pazukdev.backend.characteristic.Characteristic;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pazukdev.backend.util.SpecificStringUtil.*;

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

    public List<String> getStringValues(final Characteristic characteristic) {
        return getList(getStringValue(characteristic));
    }

    public String getStringValue(final Characteristic characteristic) {
        return getString(data.get(transformToKey(characteristic)));
    }

    public String getStringValueBeforeParenthesises(final Characteristic characteristic) {
        return getStringBeforeParentheses(getStringValue(characteristic));
    }

    public Integer getIntegerValue(final Characteristic characteristic) {
        return extractIntegerAutomatically(getStringValue(characteristic));
    }

    public Integer getProductionStartYear(final Characteristic characteristic) {
        return getIntegerBeforeDash(getStringValue(characteristic));
    }

    public Integer getProductionStopYear(final Characteristic characteristic) {
        return getIntegerAfterDash(getStringValue(characteristic));
    }

    private String transformToKey(final Characteristic characteristic) {
        return characteristic.toString().toLowerCase();
    }

}


















