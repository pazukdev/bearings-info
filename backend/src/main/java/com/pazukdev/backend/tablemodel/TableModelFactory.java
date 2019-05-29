package com.pazukdev.backend.tablemodel;

import com.opencsv.CSVReader;
import com.pazukdev.backend.util.AppCollectionUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TableModelFactory {

    public static TableModelFactory create() {
        return new TableModelFactory();
    }

    public TableModel createTableModel(final String filePath) {
        final List<TableRow> tableRows = getTableRows(readFile(filePath));
        return new TableModelImpl(tableRows);
    }

    private List<TableRow> getTableRows(final List<String[]> fileLines) {
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

        return rows;
    }

    private List<String[]> readFile(final String filePath) {
        List<String[]> lines = null;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            lines = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return format(lines);
    }

    private List<String[]> format(final List<String[]> list) {
        return AppCollectionUtil.toLowerCase(AppCollectionUtil.removeSpaces(list));
    }

    private String[] getHeader(final List<String[]> fileLines) {
        return fileLines.get(0);
    }

    private List<String[]> getBody(final List<String[]> fileLines) {
        return fileLines.subList(1, fileLines.size());
    }

}
