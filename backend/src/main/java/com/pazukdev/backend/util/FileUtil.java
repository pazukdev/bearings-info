package com.pazukdev.backend.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.pazukdev.backend.util.FileUtil.FileFormat.CSV;
import static com.pazukdev.backend.util.FileUtil.FileFormat.TXT;

public class FileUtil {

    public static class Directory {
        public static String BASIC_DIRECTORY = "backend/src/";
        public static final String STATIC_DIRECTORY = "/static/";
    }

    public static class FileFormat {
        public static final String CSV = ".csv";
        public static final String TXT = ".txt";
    }

    public static class FileName {
        public static final String COMMENTS = "comments";
        public static final String INFO_CATEGORIES = "info_categories";
        public static final String DICTIONARY = "dictionary";
    }

    public static Set<String> getTxtFileLines(final String fileName) {
        return getTxtFileLines(getTxtFilePath(fileName));
    }

    public static Set<String> getTxtFileLines(final Path path) {
        try {
            return new HashSet<>(Files.readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    public static Path getTxtFilePath(final String fileName) {
        return Paths.get(Directory.BASIC_DIRECTORY + "txt/" + fileName + TXT);
    }

    public static String[] getCSVFilesPaths() {
        final String[] fileNames = {
                "manufacturer",
                "standard",
                "material",
                "wire",
                "gasket",
                "washer",
                "fastener",
                "oil",
                "tube",
                "tire",
                "seal",
                "spark_plug",
                "lock_ring",
                "adapter",
                "oil_filter",
                "piston_rings",
                "piston_pin",
                "piston",
                "piston_assembly",
                "universal_joint_cross",
                "rolling_element",
                "cage",
                "bearing",
                "universal_joint",
                "wheel",
                "chassis",
                "generator",
                "sidecar_reduction_drive",
                "final_drive",
                "gearbox",
                "engine",
                "vehicle"
        };
        final List<String> paths = new ArrayList<>();
        for (final String fileName : fileNames) {
            paths.add(dataFilePathInResources(fileName));
        }
        return paths.toArray(new String[0]);
    }

    private static String dataFilePathInResources(final String fileName) {
        return Directory.STATIC_DIRECTORY + fileName + CSV;
    }

//    public static void createDictionaryFileInFileSystem(final String base64Data) throws IOException {
//        createDictionaryFileInFileSystem(Base64.getDecoder().decode(base64Data.getBytes(StandardCharsets.UTF_8)));
//    }

    public static void createFileInFileSystem(final String fileName, final byte[] text) throws IOException {
        Files.write(getTxtFilePath(fileName), text);
    }

    public static void createFile(final String fileName, final Set<String> textLines) {
        try {
            Files.write(getTxtFilePath(fileName), getSortedFileLines(textLines), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String getDictionaryFilePathString() {
//        return Directory.BASIC_DIRECTORY + "language/" + DICTIONARY + TXT;
//    }

    public static List<String> getSortedFileLines(final Set<String> textLines) {
        final List<String> sortedFileContent = new ArrayList<>(textLines);
        sortedFileContent.sort(String::compareTo);
        return sortedFileContent;
    }

}
