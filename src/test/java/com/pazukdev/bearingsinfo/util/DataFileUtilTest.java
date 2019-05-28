package com.pazukdev.bearingsinfo.util;

import org.junit.Test;

/**
 * @author Siarhei Sviarkaltsau
 */
public class DataFileUtilTest {

    @Test
    public void testOnMotorcycleFile() {
        final String filePath = CSVFileUtil.motorcycleDataFilePath();
        final String characteristicNamesSource = "|name|manufacturer|production|weight_kg|bearing|seal|";
        final String firstProductData = "|m-72|imz|1941-1957|300|207,205,204|7201191,7201122|";

        validate(filePath, characteristicNamesSource, firstProductData);
    }

    @Test
    public void testOnBearingFile() {
        final String filePath = CSVFileUtil.bearingDataFilePath();
        final String characteristicNamesSource = "|name|type|rolling_element|enclosure|rolling_row|cage|size|weight_g|dynamic|replacer|";
        final String firstProductData = "|209|deepgroove|ball(9)|open|single_row|sheet_metal|45x85x19|-|-|6209|";

        validate(filePath, characteristicNamesSource, firstProductData);
    }

    @Test
    public void testOnSealFile() {
        final String filePath = CSVFileUtil.sealDataFilePath();
        final String characteristicNamesSource = "|name|size|rotation|material|replacer|";
        final String firstProductData = "|7201191|50x70x9|left|rubber|corteco_12011238b|";

        validate(filePath, characteristicNamesSource, firstProductData);
    }

    private void validate(final String filePath,
                          final String characteristicNamesSource,
                          final String firstProductData) {
//        final CSVFileContent content = CSVUtil.parse(filePath);
//
//        assertEquals(characteristicNamesSource, content.getCharacteristicNamesSource());
//
//        final List<String> productsData = content.getProductsData();
//        assertNotNull(productsData);
//        assertTrue(productsData.size() > 0);
//        assertEquals(firstProductData, productsData.get(0));
    }

}
