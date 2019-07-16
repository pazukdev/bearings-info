package com.pazukdev.backend.tablemodel;

import com.pazukdev.backend.util.CSVFileUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableModelFactory {

    public static TableModelFactory create() {
        return new TableModelFactory();
    }

    public TableModel createTableModel(final File file) {
        final List<TableRow> tableRows = getTableRows(CSVFileUtil.readFile(file));
        return new TableModelImpl(tableRows);
    }

    private List<TableRow> getTableRows(final List<String[]> fileLines) {
        final List<TableRow> rows = new ArrayList<>();
        final String[] header = getHeader(fileLines);
        final List<String[]> body = getBody(fileLines);

        for (final String[] line : body) {
            final TableRow row = TableRow.create();
            for (int i = 0; i < line.length; i++) {
                row.put(header[i], line[i]);
            }
            rows.add(row);
        }

        return rows;
    }

    private String[] getHeader(final List<String[]> fileLines) {
        return fileLines.get(0);
    }

    private List<String[]> getBody(final List<String[]> fileLines) {
        return fileLines.subList(1, fileLines.size());
    }

}
