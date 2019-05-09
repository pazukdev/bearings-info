package com.pazukdev.bearingsinfo.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * @author Siarhei Sviarkaltsau
 */
public class SpecificStringUtil {

    public static Integer getIntegerBetweenParentheses(@Nullable final String source) {
        return getCheckedInteger(getStringBetweenParentheses(source));

    }

    public static String getStringBetweenParentheses(@Nullable final String source) {
        return getCheckedString(StringUtils.substringBetween(source, "(", ")"));
    }

    public static String getStringBeforeParentheses(@Nullable final String source) {
        return getCheckedString(source.split("\\(")[0]);

    }

    public static boolean hasNoData(@Nullable final String source) {
        return !hasData(source);
    }

    public static boolean hasData(@Nullable final String source) {
        return isNotEmpty(source) && source.contains("|");
    }

    public static boolean isNotEmpty(@Nullable final String data) {
        return !isEmpty(data);
    }

    public static boolean isEmpty(@Nullable final String data) {
        return StringUtils.isEmpty(data) || data.equals("null") || data.equals("-");
    }

    private static String getCheckedString(@Nullable final String data) {
        return isNotEmpty(data) ? data : null;
    }

    private static Integer getCheckedInteger(@Nullable final String data) {
        return isNotEmpty(data) ? Integer.valueOf(data) : null;
    }

}
