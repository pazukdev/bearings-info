package com.pazukdev.backend.unit.factory;

import com.pazukdev.backend.tablemodel.TableModel;
import com.pazukdev.backend.tablemodel.TableModelFactory;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityFactoryTest {

    @Test
    public void createTableModelFromItemSourceFile() {
        final TableModelFactory factory = TableModelFactory.create();
        final String filePath = CSVFileUtil.filePath("item");
        final TableModel tableModel = factory.createTableModel(filePath);
        for (TableRow tableRow : tableModel.getTableRows()) {
            System.out.println(tableRow);
        }
    }

    @Test
    public void removeEmptyFromFileLines() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(new ArrayList<>(Arrays.asList("", "")));
        lists.add(new ArrayList<>(Arrays.asList("1", "")));
        System.out.println(lists);
        TableModelFactory factory = TableModelFactory.create();
        System.out.println(factory.removeEmptyLines(lists));
        System.out.println(factory.removeEmptyElements(lists));
    }

    @Test
    public void categorizeFileLines() {
        List<List<String>> fileLines = new ArrayList<>();
        fileLines.add(new ArrayList<>(Arrays.asList("Category:", "bearing")));
        fileLines.add(new ArrayList<>(Arrays.asList("header1", "header2")));
        fileLines.add(new ArrayList<>(Arrays.asList("value1", "value2")));
        fileLines.add(new ArrayList<>(Arrays.asList("category:", "seal")));
        fileLines.add(new ArrayList<>(Arrays.asList("header3", "header4")));
        fileLines.add(new ArrayList<>(Arrays.asList("value3", "value4")));
        for (final List<String> fileLine : fileLines) {
            System.out.println(fileLine);
        }
        System.out.println();
        TableModelFactory factory = TableModelFactory.create();
        for (List<List<String>> categorizedFileLines : factory.categorize(fileLines)) {
            System.out.println(categorizedFileLines);
        }
    }

}
