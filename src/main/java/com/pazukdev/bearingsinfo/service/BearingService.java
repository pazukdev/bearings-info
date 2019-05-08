package com.pazukdev.bearingsinfo.service;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if (repository.findAll().isEmpty()) {
            saveDefaultBearings();
        }
        return repository.findAll().stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    private void saveDefaultBearings() {
        final List<String> dataList = getDataListFromTxtFile();
        final List<String> bearingsData = dataList.subList(1, dataList.size());
        final String characteristicNamesSource = dataList.get(0);

        logData(characteristicNamesSource, bearingsData);

        for (String characteristicDataSource : bearingsData) {
            final BearingDto bearingDto = factory.create(characteristicNamesSource, characteristicDataSource);
            createBearing(bearingDto);
        }
    }

    private List<String> getDataListFromTxtFile() {

        final String path = "./src/main/resources/defaultdata/bearings.txt";

        List<String> lines = null;

        try (final BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private void logData(final String characteristicNamesSource, final List<String> bearingsData) {
        logger.info("characteristicNames:");
        logger.info(characteristicNamesSource);
        logger.info("bearingsData:");
        bearingsData.forEach(logger::info);
    }

}
