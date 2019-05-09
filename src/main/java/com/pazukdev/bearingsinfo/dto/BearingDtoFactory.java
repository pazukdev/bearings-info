package com.pazukdev.bearingsinfo.dto;

import com.pazukdev.bearingsinfo.BearingCharacteristic;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.getIntegerBetweenParentheses;
import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.getStringBeforeParentheses;
import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.isEmpty;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class BearingDtoFactory {

    public BearingDto create(@NotNull final String characteristicNamesSource,
                             @NotNull final String characteristicDataSource) {
        final List<BearingCharacteristic> characteristicsList;
        final List<String> bearingData;
        final Map <BearingCharacteristic, String> characteristics;

        characteristicsList = parseCharacteristicNamesSourceString(characteristicNamesSource);
        bearingData = parseCharacteristicDataSourceString(characteristicDataSource);
        characteristics = createCharacteristicsMap(characteristicsList, bearingData);

        final BearingDto bearingDto = new BearingDto();
        applyCharacteristics(bearingDto, characteristics);
        return bearingDto;
    }

    private Map<BearingCharacteristic, String> createCharacteristicsMap(@NotNull final List<BearingCharacteristic> characteristicsList,
                                                                        @NotNull final List<String> characteristicData) {

        final Map <BearingCharacteristic, String> characteristics = new HashMap<>();

        for (int i = 0; i < characteristicsList.size(); i++) {
            characteristics.put(characteristicsList.get(i), characteristicData.get(i));
        }
        return characteristics;
    }

    private List<BearingCharacteristic> parseCharacteristicNamesSourceString(@NotNull final String source) {
        return createBearingCharacteristicsList(parseSourceString(source));
    }

    private List<String> parseCharacteristicDataSourceString(@NotNull final String source) {
        return parseSourceString(source);
    }

    private List<String> parseSourceString(@NotNull String source) {
        String noSpacesString = source.replaceAll(" ", "");
        return Arrays.asList((noSpacesString.substring(1, noSpacesString.length() - 1)).split("\\|"));
    }

    private List<BearingCharacteristic> createBearingCharacteristicsList(@NotNull final List<String> source) {
        final List<BearingCharacteristic> characteristics = new ArrayList<>();

        for (final String characteristicName : source) {
            BearingCharacteristic characteristic = null;
            try {
                characteristic = BearingCharacteristic.valueOf(characteristicName);
            } catch (IllegalArgumentException e) {}
            characteristics.add(characteristic);
        }
        return characteristics;
    }

    private void applyCharacteristics(@NotNull final BearingDto bearingDto,
                                      @NotNull final Map<BearingCharacteristic, String> characteristics) {
        applyName(bearingDto, characteristics);
        applyType(bearingDto, characteristics);
        applyRollingElement(bearingDto, characteristics);
        applyRollingElementsQuantity(bearingDto, characteristics);
    }

    private void applyName(@NotNull final BearingDto bearingDto,
                           @NotNull final Map<BearingCharacteristic, String> characteristics) {
        final String data= characteristics.get(BearingCharacteristic.NAME);

        if (isEmpty(data)) return;
        bearingDto.setName(data);
    }

    private void applyType(@NotNull final BearingDto bearingDto,
                           @NotNull final Map<BearingCharacteristic, String> characteristics) {
        final String data= characteristics.get(BearingCharacteristic.TYPE);

        if (isEmpty(data)) return;
        bearingDto.setType(data);
    }

    private void applyRollingElement(@NotNull final BearingDto bearingDto,
                                     @NotNull final Map<BearingCharacteristic, String> characteristics) {
        final String data= characteristics.get(BearingCharacteristic.ROLLING_ELEMENT);
        final String rollingElementData = getStringBeforeParentheses(data);

        if (isEmpty(rollingElementData)) return;
        bearingDto.setRollingElement(rollingElementData);
    }

    private void applyRollingElementsQuantity(@NotNull final BearingDto bearingDto,
                                              @NotNull final Map<BearingCharacteristic, String> characteristics) {
        final String data= characteristics.get(BearingCharacteristic.ROLLING_ELEMENT);
        final Integer rollingElementsQuantity = getIntegerBetweenParentheses(data);

        if (rollingElementsQuantity == null) return;
        bearingDto.setRollingElementsQuantity(rollingElementsQuantity);
    }

}
















