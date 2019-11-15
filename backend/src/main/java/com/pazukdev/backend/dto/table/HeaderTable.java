package com.pazukdev.backend.dto.table;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.NONE)
public class HeaderTable extends AbstractDto {

    private List<HeaderTableRow> rows;

    public static HeaderTable createStubTable() {
        final HeaderTableRow row = HeaderTableRow.create("", "");
        final HeaderTable headerTable = new HeaderTable();
        headerTable.setName("stub");
        headerTable.setRows(new ArrayList<>(Collections.singletonList(row)));
        return headerTable;
    }

    public static HeaderTable create(final String tableName, final List<HeaderTableRow> rows) {
        final HeaderTable headerTable = new HeaderTable();
        headerTable.setName(tableName);
        headerTable.setRows(rows);
        return headerTable;
    }

    public static HeaderTable createSingleRowTable(final String tableName, final HeaderTableRow row) {
        final HeaderTable headerTable = new HeaderTable();
        headerTable.setName(tableName);
        headerTable.setRows(new ArrayList<>(Collections.singletonList(row)));
        return headerTable;
    }

//    private static List<HeaderTableRow> createHeaderTableRowsList(final List<String[]> rows) {
//        final List<HeaderTableRow>  headerTableRows = new ArrayList<>();
//        for (final String[] row : rows) {
//            final String parameter = row[0];
//            final String value = row[1];
//            headerTableRows.add(HeaderTableRow.create(parameter, value));
//        }
//        return headerTableRows;
//    }

}
