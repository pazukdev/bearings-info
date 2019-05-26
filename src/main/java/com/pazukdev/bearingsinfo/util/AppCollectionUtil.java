package com.pazukdev.bearingsinfo.util;

import java.util.List;
import java.util.Objects;

/**
 * @author Siarhei Sviarkaltsau
 */
public class AppCollectionUtil {

    public static List<String> toLowerCase(final List<String> list) {
        Objects.requireNonNull(list).replaceAll(String::toLowerCase);
        return list;
    }

    public static List<String> toUpperCase(final List<String> list) {
        Objects.requireNonNull(list).replaceAll(String::toUpperCase);
        return list;
    }

    public static List<String> removeSpaces(final List<String> list) {
        Objects.requireNonNull(list).replaceAll(SpecificStringUtil::removeSpaces);
        return list;
    }

}
