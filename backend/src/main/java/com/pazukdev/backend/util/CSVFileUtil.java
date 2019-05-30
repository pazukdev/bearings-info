package com.pazukdev.backend.util;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * @author Siarhei Sviarkaltsau
 */
public class CSVFileUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVFileUtil.class);

    private static final String PACKAGE = "static/";
    private static final String FILE_FORMAT = "csv";
    private static final String MOTORCYCLE_FILE_NAME = "motorcycle";
    private static final String BEARING_FILE_NAME = "bearing";
    private static final String SEAL_FILE_NAME = "seal";

    public static File motorcycleFile() {
        return file(MOTORCYCLE_FILE_NAME);
    }

    public static File bearingFile() {
        return file(BEARING_FILE_NAME);
    }

    public static File sealFile() {
        return file(SEAL_FILE_NAME);
    }

    public static List<String[]> readFile(final File file) {
        List<String[]> lines = null;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            lines = reader.readAll();
        } catch (IOException e) {
            LOGGER.error("Error collecting data from .csv file: " + file.getName(), e);
        }
        return format(lines);
    }

    private static List<String[]> format(final List<String[]> list) {
        return AppCollectionUtil.toLowerCase(AppCollectionUtil.removeSpaces(list));
    }

    private static Path path(final String filePathInResources) {
        final ClassLoader classLoader = CSVFileUtil.class.getClassLoader();
        final URL url = classLoader.getResource(filePathInResources);

        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            LOGGER.error("Error getting path from: " + filePathInResources, e);
        }
        return path;
    }

    private static File file(final String fileName) {
        return Objects.requireNonNull(path(dataFilePathInResources(fileName))).toFile();
    }

    private static String dataFilePathInResources(final String fileName) {
        return PACKAGE + fileName + "." + FILE_FORMAT;
    }

}
