package com.pazukdev.bearingsinfo.util;

import com.pazukdev.bearingsinfo.DataFileContent;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.pazukdev.bearingsinfo.util.AppCollectionUtil.removeSpaces;
import static com.pazukdev.bearingsinfo.util.AppCollectionUtil.toLowerCase;

/**
 * @author Siarhei Sviarkaltsau
 */
public class DataFileUtil {

    private static final String DEFAULT_DATA_PATH = "./src/main/resources/defaultdata/";
    private static final String MOTORCYCLE_DATA_FILE_NAME = "motorcycle.txt";
    private static final String BEARING_DATA_FILE_NAME = "bearing.txt";
    private static final String SEAL_DATA_FILE_NAME = "seal.txt";

    public static DataFileContent parse(final String filePath) {
        final List<String> dataList = getDataListFromTxtFile(filePath);
        final List<String> productsData = getProductsData(dataList);
        final String characteristicNamesSource = getCharacteristicNamesSource(dataList);

        return new DataFileContent(productsData, characteristicNamesSource);
    }

    public static String motorcycleDataFilePath() {
        return DEFAULT_DATA_PATH + MOTORCYCLE_DATA_FILE_NAME;
    }

    public static String bearingDataFilePath() {
        return DEFAULT_DATA_PATH + BEARING_DATA_FILE_NAME;
    }

    public static String sealDataFilePath() {
        return DEFAULT_DATA_PATH + SEAL_DATA_FILE_NAME;
    }

    public static void logData(final Logger logger,
                               final String characteristicNamesSource,
                               final List<String> dataList) {
        logger.info("characteristicNames:");
        logger.info(characteristicNamesSource);
        logger.info("data:");
        dataList.forEach(logger::info);
    }

    private static List<String> getDataListFromTxtFile(final String path) {

        List<String> lines = null;

        try (final BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toLowerCase(removeSpaces(lines));
    }

    private static String getCharacteristicNamesSource(final List<String> dataList) {
        return removeNonDataElements(dataList).get(0);
    }

    public static List<String> getProductsData(final List<String> dataList) {
        final List<String> checkedDataList = removeNonDataElements(dataList);
        return checkedDataList.subList(1, checkedDataList.size());
    }

    private static List<String> removeNonDataElements(final List<String> dataList) {
        dataList.removeIf(SpecificStringUtil::hasNoData);
        return dataList;
    }

}
