package com.pazukdev.bearingsinfo.util;

/**
 * @author Siarhei Sviarkaltsau
 */
public class CSVFileUtil {

    private static final String DEFAULT_DATA_PATH = "./src/main/resources/defaultdata/";
    private static final String FILE_FORMAT = "csv";
    private static final String MOTORCYCLE_DATA_FILE_NAME = "motorcycle";
    private static final String BEARING_DATA_FILE_NAME = "bearing";
    private static final String SEAL_DATA_FILE_NAME = "seal";

    public static String motorcycleDataFilePath() {
        return dataFilePath(MOTORCYCLE_DATA_FILE_NAME);
    }

    public static String bearingDataFilePath() {
        return dataFilePath(BEARING_DATA_FILE_NAME);
    }

    public static String sealDataFilePath() {
        return dataFilePath(SEAL_DATA_FILE_NAME);
    }

    private static String dataFilePath(final String fileName) {
        return DEFAULT_DATA_PATH + fileName + "." + FILE_FORMAT;
    }

}
