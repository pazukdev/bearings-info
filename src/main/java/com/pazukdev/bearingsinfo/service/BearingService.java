package com.pazukdev.bearingsinfo.service;

import com.pazukdev.bearingsinfo.util.DataFileUtil;
import com.pazukdev.bearingsinfo.Main;
import com.pazukdev.bearingsinfo.converter.BearingConverter;
import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;
import com.pazukdev.bearingsinfo.dto.BearingDtoFactory;
import com.pazukdev.bearingsinfo.repository.BearingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
@Service
@RequiredArgsConstructor
public class BearingService {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final BearingRepository repository;
    private final BearingConverter converter;
    private final BearingDtoFactory factory;

    public void createBearing(final BearingDto bearingDto) {
        final Bearing bearing = converter.convertToDbo(bearingDto);
        repository.save(bearing);
    }

    public List<BearingDto> getBearingsList() {
        List<Bearing> bearingList = repository.findAll();
        if (bearingList.isEmpty()) {
            createDefaultBearings();
            bearingList = repository.findAll();
        }
        return convertToDtoList(bearingList);
    }

    private List<BearingDto> convertToDtoList(final List<Bearing> bearingList) {
        return bearingList.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    private void createDefaultBearings() {
        final List<String> dataList = getDataListFromTxtFile();
        final List<String> bearingsData = DataFileUtil.getBearingsData(dataList);
        final String characteristicNamesSource = DataFileUtil.getCharacteristicNamesSource(dataList);
        DataFileUtil.logData(logger, characteristicNamesSource, bearingsData);

        createBearings(characteristicNamesSource, bearingsData);
    }

    private void createBearings(@NotNull final String characteristicNamesSource,
                                @NotNull final List<String> bearingsData) {
        for (String characteristicDataSource : bearingsData) {
            final BearingDto bearingDto = factory.create(characteristicNamesSource, characteristicDataSource);
            createBearing(bearingDto);
        }
    }

    private List<String> getDataListFromTxtFile() {
        final String path = "./src/main/resources/defaultdata/bearings.txt";
        return DataFileUtil.getDataListFromTxtFile(path);
    }

}
