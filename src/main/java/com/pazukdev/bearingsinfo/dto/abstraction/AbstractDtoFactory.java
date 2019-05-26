package com.pazukdev.bearingsinfo.dto.abstraction;

import com.pazukdev.bearingsinfo.DataFileContent;
import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.util.AppCollectionUtil;
import com.pazukdev.bearingsinfo.util.DataFileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.isEmpty;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class AbstractDtoFactory<Dto extends AbstractDto> {

    public List<Dto> createFromDataFile() {
        return createFromDataFile(obtainContent());
    }

    protected abstract DataFileContent obtainContent();

    protected abstract Dto createDto();

    protected abstract void applyCharacteristics(final Dto dto,
                                                 final Map<Characteristic, String> characteristics);

    protected DataFileContent parse(final String filePath) {
        return DataFileUtil.parse(filePath);
    }

    private List<Dto> createFromDataFile(final DataFileContent content) {
        final String characteristicNamesSource = content.getCharacteristicNamesSource();
        List<Dto> dtos = new ArrayList<>();

        for (final String dtoData : content.getProductsData()) {
            dtos.add(create(characteristicNamesSource, dtoData));
        }

        return dtos;
    }

    private Dto create(final String characteristicNamesSource,
                       final String characteristicDataSource) {
        final List<Characteristic> characteristicsList;
        final List<String> dtoData;
        final Map <Characteristic, String> characteristics;

        characteristicsList = parseCharacteristicNamesSourceString(characteristicNamesSource);
        dtoData = parseCharacteristicDataSourceString(characteristicDataSource);
        characteristics = createCharacteristicsMap(characteristicsList, dtoData);

        return getDtoWithAppliedCharacteristics(characteristics);
    }

    private Dto getDtoWithAppliedCharacteristics(final Map <Characteristic, String> characteristics) {
        final Dto dto = createDto();
        applyCharacteristics(dto, characteristics);
        return dto;
    }

    private Map<Characteristic, String> createCharacteristicsMap(final List<Characteristic> characteristicsList,
                                                                 final List<String> characteristicData) {

        final Map <Characteristic, String> characteristics = new HashMap<>();

        for (int i = 0; i < characteristicsList.size(); i++) {
            characteristics.put(characteristicsList.get(i), characteristicData.get(i));
        }
        return characteristics;
    }

    private List<Characteristic> parseCharacteristicNamesSourceString(final String source) {
        return createCharacteristicsList(parseSourceString(source));
    }

    private List<String> parseCharacteristicDataSourceString(final String source) {
        return parseSourceString(source);
    }

    private List<String> parseSourceString(String source) {
        String noSpacesString = source.replaceAll(" ", "");
        return Arrays.asList((noSpacesString.substring(1, noSpacesString.length() - 1)).split("\\|"));
    }

    private List<Characteristic> createCharacteristicsList(final List<String> source) {
        final List<Characteristic> characteristics = new ArrayList<>();

        for (final String characteristicName : AppCollectionUtil.toUpperCase(source)) {
            Characteristic characteristic = null;
            try {
                characteristic = Characteristic.valueOf(characteristicName);
            } catch (IllegalArgumentException e) {}
            characteristics.add(characteristic);
        }
        return characteristics;
    }

    protected void applyName(final AbstractDto abstractDto,
                             final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.NAME);

        if (isEmpty(data)) return;
        abstractDto.setName(data);
    }

}
