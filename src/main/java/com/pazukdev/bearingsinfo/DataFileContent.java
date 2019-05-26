package com.pazukdev.bearingsinfo;

import lombok.Data;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class DataFileContent {

    private final List<String> productsData;
    private final String characteristicNamesSource;

}
