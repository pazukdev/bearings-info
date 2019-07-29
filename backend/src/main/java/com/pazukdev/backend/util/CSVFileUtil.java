package com.pazukdev.backend.util;

import com.opencsv.CSVReader;
import com.pazukdev.backend.entity.AbstractEntity;
import com.pazukdev.backend.entity.AbstractEntityFactory;
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

    public static final String PACKAGE = "static/";
    public static final String FILE_FORMAT = "csv";
    public static final String MANUFACTURER_FILE_NAME = "manufacturer";
    public static final String MOTORCYCLE_FILE_NAME = "motorcycle";
    public static final String BEARING_FILE_NAME = "bearing";
    public static final String SEAL_FILE_NAME = "seal";
    public static final String VALVE_FILE_NAME = "valve";
    public static final String VALVE_GUIDE_FILE_NAME = "valve_guide";
    public static final String OIL_FILE_NAME = "oil";
    public static final String BOLT_NUT_FILE_NAME = "bolt_nut";
    public static final String CARBURETOR_FILE_NAME = "carburetor";
    public static final String GEARBOX_FILE_NAME = "gearbox";
    public static final String ENGINE_FILE_NAME = "engine";
    public static final String GENERATOR_FILE_NAME = "generator";
    public static final String FINAL_DRIVE_FILE_NAME = "final_drive";
    public static final String SIDECAR_REDUCTION_DRIVE_FILE_NAME = "sidecar_reduction_drive";
    public static final String PISTON_FILE_NAME = "piston";
    public static final String SPARK_PLUG_FILE_NAME = "spark_plug";
    public static final String WHEEL_FILE_NAME = "wheel";
    public static final String FRAME_FILE_NAME = "frame";
    public static final String CYLINDER_HEAD_FILE_NAME = "cylinder_head";

    public static File file(final String fileName) {
        return Objects.requireNonNull(path(dataFilePathInResources(fileName))).toFile();
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

    public static <E extends AbstractEntity> E findByName(final String name,
                                                          final AbstractEntityFactory<E> factory) {
        return factory.findByName(name);
    }

    public static <E extends AbstractEntity> E getFirstEntity(final AbstractEntityFactory<E> factory) {
        return factory.createEntitiesFromCSVFile().get(0);
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

    private static String dataFilePathInResources(final String fileName) {
        return PACKAGE + fileName + "." + FILE_FORMAT;
    }

}



















