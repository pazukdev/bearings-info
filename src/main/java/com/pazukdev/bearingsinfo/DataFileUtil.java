package com.pazukdev.bearingsinfo;

import com.pazukdev.bearingsinfo.util.SpecificStringUtils;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
public class DataFileUtil {

    public static List<String> getDataListFromTxtFile(final String path) {

        List<String> lines = null;

        try (final BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static void logData(final Logger logger,
                               final String characteristicNamesSource,
                               final List<String> dataList) {
        logger.info("characteristicNames:");
        logger.info(characteristicNamesSource);
        logger.info("data:");
        dataList.forEach(logger::info);
    }

    public static String getCharacteristicNamesSource(final List<String> dataList) {
        return removeNonDataElements(dataList).get(0);
    }

    public static List<String> getBearingsData(final List<String> dataList) {
        final List<String> checkedDataList = removeNonDataElements(dataList);
        return checkedDataList.subList(1, checkedDataList.size());
    }

    private static List<String> removeNonDataElements(final List<String> dataList) {
        dataList.removeIf(SpecificStringUtils::hasNoData);
        return dataList;
    }

}
