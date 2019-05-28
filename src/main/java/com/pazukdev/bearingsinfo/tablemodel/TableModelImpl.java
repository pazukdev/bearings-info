package com.pazukdev.bearingsinfo.tablemodel;

import com.opencsv.CSVReader;
import com.pazukdev.bearingsinfo.util.AppCollectionUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TableModelImpl implements TableModel {

    private final List<TableRow> tableRows;

    public static TableModelImpl create(final String filePath) {
        final List<String[]> fileLines = readFile(filePath);
        return create(fileLines);
    }

    private static TableModelImpl create(final List<String[]> fileLines) {
        final List<TableRow> rows = new ArrayList<>();
        final String[] header = getHeader(fileLines);
        final List<String[]> body = getBody(fileLines);

        for (final String[] line : body) {
            final TableRow row = TableRow.create();
            for (int i = 0; i < line.length - 1; i++) {
                row.put(header[i], line[i]);
            }
            rows.add(row);
        }

        return new TableModelImpl(rows);
    }

    private static List<String[]> readFile(final String filePath) {
        List<String[]> lines = null;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            lines = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return format(lines);
    }

    private static List<String[]> format(final List<String[]> list) {
        return AppCollectionUtil.toLowerCase(AppCollectionUtil.removeSpaces(list));
    }

    private static String[] getHeader(final List<String[]> fileLines) {
        return fileLines.get(0);
    }

    private static List<String[]> getBody(final List<String[]> fileLines) {
        return fileLines.subList(1, fileLines.size());
    }

}
