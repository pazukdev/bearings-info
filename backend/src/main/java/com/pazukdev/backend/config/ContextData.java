package com.pazukdev.backend.config;

import java.util.*;

public class ContextData {

    public static final String NAME = "name";
    public static final String FULL_NAME = "full name";
    public static final String MANUFACTURER = "manufacturer";
    public static final String PRODUCTION = "production";
    public static final String COUNTRY = "country";
    public static final String FOUNDED = "founded";
    public static final String DEFUNCT = "defunct";

    private static final List<String> fixedParams = new ArrayList<>(Arrays
            .asList(NAME, FULL_NAME, PRODUCTION, MANUFACTURER));

    private static final List<String> descriptionIgnore = new ArrayList<>(
            Arrays.asList(NAME, "category", "replacer", "image", "website", "website lang", "wiki"));

    private static final Map<String, Integer> parametersWeight = new HashMap<String, Integer>() {{
        put(NAME, 100);
        put(FULL_NAME, 99);
        put(COUNTRY, 98);
        put(FOUNDED, 50);
        put(DEFUNCT, 49);
    }};

    public static boolean isFixed(final String parameter) {
        return fixedParams.contains(parameter.toLowerCase());
    }

    public static Integer getWeight(final String parameter) {
        final Integer weight = parametersWeight.get(parameter.toLowerCase());
        return weight != null ? weight : 0;
    }

    public static boolean isDescriptionIgnored(final String parameter) {
        return descriptionIgnore.contains(parameter.toLowerCase());
    }

}
