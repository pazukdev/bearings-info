package com.pazukdev.backend.util;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.pazukdev.backend.util.FileUtil.FileFormat.TXT;

/**
 * @author Siarhei Sviarkaltsau
 */
public class FileUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVFileUtil.class);

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

    public static List<String> getTxtFileTextLines(final String fileName) {
        return getTxtFileTextLines(getTxtFilePath(fileName));
    }

    public static List<String> getTxtFileTextLines(final Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Path getTxtFilePath(final String fileName) {
        return Paths.get(Directory.BASIC_DIRECTORY + "txt/" + fileName + TXT);
    }

    public static List<List<String>> readInputStreamFromCSVFile(final InputStream in) {
        List<String[]> lines = null;
        try (final CSVReader reader = new CSVReader(new InputStreamReader(in))) {
            lines = reader.readAll();
        } catch (IOException e) {
            LOGGER.error("Error collecting data from input stream", e);
        }
        return AppCollectionUtil.listOfArraysToListOfLists(lines);
    }

    public static String[] getGoogleSheetsUrls() {
        final String[] fileNames = {
                "1N6Y6uavkv6AVzIF5-NbRlXOZTJekHFBrtF8o4VvtDxc", // manufacturer
                "12rB_t_ImCGscKiGPazc4WodiG0b9G4zOOfTW1aIbbak", // standard
                "1R2haf5EqWqC6YJ4GJkcwDyTBB_1ItU_wk3l5zI9CQjQ", // "material"
                "1Kc6IIZNozbgpjwAzYbiLSnPqihOTKxzZ3lwQXO0QZa0", // "cylinder"
                "1SJcfsoOmpMA5auCEWprmfu5_5hyL_LQTPrT95fuKyvk", // "wire"
                "1qtV8j8nHb8u3gyJtrByI6diDPlVHsYl7X0j4HqgOSik", // "gasket"
                "1LzrET_v01fg-BdtMV8No7nnhBDZxZSAtUMfLwvX2bCA", // "washer"
                "1upjGjUKjtr1tk3RxQ5kR8TKAPbJhhZ-yNwuKwm5Sow4", // "fastener"
                "1Epb-XZlBRUG3lVya_Ps8tz7sLQFxLcO_ezM_Yxsg4tA", // "oil"
                "1SoU8eSo74SGvdbSnx95eOdGST-_Yq8FlzDcXqcr7AB4", // "tube"
                "1hDzM8xIDnqW8GwV1smFTwwzVzlUz_QZcYFx_AFBjSqg", // "tire"
                "1JJrNlsIyT1fCpEu9SKDpYF_CwYgljCqtET3k9i2sGIY", // "seal"
                "1cGNnAl-nkH-pRsnGpeUhfbV7mBe6mTHQq0fg-dhZGvk", // "spark_plug"
                "1OQydFcMrVbis1acHjgoUZ6Q7dbSbj7e2GnImrWL7Bf0", // "lock_ring"
                "1t2GRH9l6PeE31NV44Po2jheeO6pokGyv-1wzrOyL0bs", // "adapter"
                "1-wjosk677Z3iBk-gXA7WgryffXdpsD0pcPk8ld6U3Uo", // "oil_filter"
                "1Gt2zBapfxHsK9ckkRl05IrVr6gl8ud3VmhAvzg71xI8", // "piston_rings"
                "1RsCg2OtsIyUOsbKUVFuvASjOmLJW2GzHU_mZNN4fpWc", // "piston_pin"
                "1Zm2FYY_jMduOaGNQgkCfvgi_pkga1hFHdf1lvy8mj20", // "piston"
                "1WZQ0cFKzwmkUshVtsHAHYmspOV1xrL3WtHgq18uyRKc", // "piston_assembly"
                "1fTjB2FmhIKGvnbE88Cn4ev-qxee1pmt0PYX4QujnGh4", // "universal_joint_cross"
                "1aVsbVKPvEj4vkGAdPtPefnohfB58OKCLFAchgDEyaPc", // "rolling_element"
                "1MN2EPwmY4ob0P1UD-S6tQ1ArjNzlBpVXCsrIOAeq1O4", // "cage"
                "1q8gBtUsPx1nept2SrOqwKdd4soEtYTXiDGaWp78iiT8", // "bearing"
                "1JVkzi1VtZ0UnNDXA8t9Ie3KSfXcUqijpD2n-CUuXI_c", // "rubber_part"
                "1MgrDl6OSBNxp0NgwxqV5atq9UC6D5b4F-2u-Us3Ha00", // "universal_joint"
                "1_DwfWapbBDnyycxxjqEMJKbFv3TK7-9G3jqgRkUcRSM", // "wheel"
                "1TGB_CUz-bsAwYdijOKDh9PbGHP0TXVIkpMavXWJfYRE", // "chassis"
                "1Sg1LQgSpcwUNUUV_PMAcZVHgPxIi3hRoULYP_waET_c", // "generator"
                "1whfpZjfZk2TlmqdUwV60sOJmVoZMc8sBcDq8Lwldcx0", // "sidecar_reduction_drive"
                "12pIYehuWhEmwhhzlcRKxRm2rSFsDytJyu6RDOMQl4xY", // "final_drive"
                "1GAHw2DFmyYdPbwQNtpxwSKrI1mTCe7XTLmPhDIvZhc4", // "gearbox"
                "1VlxspbLk6Vm4aOQnNaJJE6Z_b7bgs5tKABS8CtXHfZU", // "engine"
                "1TmLYCnMjXN5HOqRQ5QD32nQrL4XsTbdeYpS3CfLF3lo" // "vehicle"
        };
        final String basicUrl = "https://docs.google.com/spreadsheets/d/";
        final List<String> paths = new ArrayList<>();
        for (final String fileName : fileNames) {
            paths.add(basicUrl + fileName);
        }
        return paths.toArray(new String[0]);
    }

    public static void createFileInFileSystem(final String fileName, final byte[] text) throws IOException {
        Files.write(getTxtFilePath(fileName), text);
    }

    public static void createFile(final String fileName, final List<String> textLines) {
        try {
            Files.write(getTxtFilePath(fileName), getSortedFileLines(textLines), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSortedFileLines(final List<String> textLines) {
        textLines.sort(String::compareTo);
        return textLines;
    }

    public static URL getGoogleDocDocumentExportUrl(final String fileUrl, final String exportFormat) {
        try {
            return new URL(fileUrl + "/export?format=" + exportFormat);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
