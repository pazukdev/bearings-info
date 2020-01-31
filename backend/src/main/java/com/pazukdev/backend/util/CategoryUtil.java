package com.pazukdev.backend.util;

import com.pazukdev.backend.entity.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.pazukdev.backend.util.AppCollectionUtil.contains;
import static com.pazukdev.backend.util.CategoryUtil.Category.*;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.*;
import static com.pazukdev.backend.util.CategoryUtil.Parameter.DescriptionIgnored.NAME;
import static com.pazukdev.backend.util.ClassUtil.getFieldsValues;
import static com.pazukdev.backend.util.FileUtil.FileName.INFO_CATEGORIES;
import static com.pazukdev.backend.util.FileUtil.getTxtFileLines;
import static com.pazukdev.backend.util.ItemUtil.getValueFromDescription;
import static com.pazukdev.backend.util.SpecificStringUtil.*;

public class CategoryUtil {

    public static class Category {

        public static final String AIR_FILTER = "Air filter";
        public static final String BEARING = "Bearing";
        public static final String CAGE = "Cage";
        public static final String ENGINE = "Engine";
        public static final String FINAL_DRIVE = "Final drive";
        public static final String FRAME_AND_WHEELS = "Frame and wheels";
        public static final String GASKET = "Gasket";
        public static final String GEARBOX = "Gearbox";
        public static final String GENERATOR = "Generator";
        public static final String LOCK_RING = "Lock ring";
        public static final String MANUFACTURER = "Manufacturer";
        public static final String MATERIAL = "Material";
        public static final String OIL = "Oil";
        public static final String OIL_FILTER  = "Oil filter";
        public static final String ROLLING_ELEMENT = "Rolling element";
        public static final String SCREW = "Screw";
        public static final String SEAL = "Seal";
        public static final String SIDECAR_REDUCTION = "Sidecar reduction";
        public static final String SIDECAR_REDUCTION_DRIVE = "Sidecar reduction drive";
        public static final String SPARK_PLUG = "Spark plug";
        public static final String STANDARD = "Standard";
        public static final String TIRE = "Tire";
        public static final String TUBE = "Tube";
        public static final String UNIVERSAL_JOINT = "Universal joint";
        public static final String UNIVERSAL_JOINT_CROSS = "Universal joint cross";
        public static final String VEHICLE = "Vehicle";
        public static final String WASHER = "Washer";
        public static final String WHEEL = "Wheel";
        public static final String WIRE = "Wire";

        // other
        public static final String USER = "User";

    }

    public static class Parameter {

        public static class DescriptionIgnored {
            public static final String CATEGORY = "Category";
            public static final String IMAGE = "Image";
            public static final String NAME = "Name";
            public static final String REPLACER = "Replacer";
            public static final String WEBSITE = "Website";
            public static final String WEBSITE_LANG = "Website lang";
            public static final String WIKI = "Wiki";
        }

        // other
        public static final String BASE = "Base";
        public static final String CORE = "Core";
        public static final String COUNTRY = "Country";
        public static final String DEFUNCT = "Defunct";
        public static final String FOUNDED = "Founded";
        public static final String FULL_NAME = "Full name";
        public static final String INSULATION = "Insulation";
        public static final String OUTER_SHIELD_MATERIAL = "Outer shield material";
        public static final String PRODUCTION = "Production";
        public static final String SIZE = "Size, mm";
        public static final String TENSION = "Tension, V";
        public static final String TYPE = "Type";
        public static final String VOLTAGE = "Voltage";
    }

    private static final List<String> fixedParams = Arrays
            .asList(NAME, FULL_NAME, PRODUCTION, MANUFACTURER, COUNTRY, FOUNDED, DEFUNCT);

//    private static final Map<String, Integer> parametersWeight = new HashMap<String, Integer>() {{
//        put(NAME, 100);
//        put(FULL_NAME, 99);
//
//        put(TYPE, 97);
//
//        put(MANUFACTURER, 80);
//        put(COUNTRY, 80);
//
//        put(FOUNDED, 50);
//        put(DEFUNCT, 49);
//
//        put(VOLTAGE, 40);
//
//        put(CORE, 30);
//        put(INSULATION, 29);
//        put(OUTER_SHIELD_MATERIAL, 28);
//    }};

    public static String getItemsManagementComment(final Item item, final Set<String> comments) {
        if (item == null || comments == null) {
            return null;
        }

        final String description = item.getDescription();
        final String category = item.getCategory();

        if (description == null || category == null) {
            return null;
        }

        String commentLine = null;
        for (final String comment : comments) {
            if (comment.split("=")[0].equalsIgnoreCase(category)) {
                commentLine = comment;
            }
        }
        if (commentLine == null) {
            return "-";
        }
        String value = "";
        for (final String s : commentLine.split("=")[1].split(";")) {
            if (containsParentheses(s)) {
                final String paramValue = getValueFromDescription(description, getStringBeforeParentheses(s));
                final String unit = getStringBetweenParentheses(s);
                value +=  paramValue + " " + unit;
            } else {
                final String paramValue = getValueFromDescription(description, s);
                value += paramValue;
            }
            value += " ";
        }
        value = value.trim();
        final Character lastChar = getLastChar(value);
        if (lastChar != null && lastChar.toString().equals("-")) {
            value = removeLastChar(value);
        }
        return value.trim();
    }

    public static boolean isFixed(final String parameter) {
        return fixedParams.contains(parameter);
    }

//    public static Integer getWeight(final String parameter) {
//        final Integer weight = parametersWeight.get(parameter);
//        return weight != null ? weight : 0;
//    }

    public static boolean isAddManufacturerName(final Item nestedItem) {
        final String category = nestedItem.getCategory();
        return category.equalsIgnoreCase(SEAL) || category.equalsIgnoreCase(SPARK_PLUG);
    }

    public static boolean isInfo(final String category, final Set<String> infoCategories) {
        if (category == null || infoCategories == null) {
            return false;
        }
        return infoCategories.contains(category);
        //return contains(getFieldsValues(Info.class), category);
    }

    public static boolean isDescriptionIgnored(final String parameter) {
        return contains(getFieldsValues(DescriptionIgnored.class), parameter);
    }

    public static boolean isPart(String category, final Set<String> infoCategories) {
        return !isInfo(category, infoCategories);
    }

    public static Set<String> getInfoCategories() {
        return getTxtFileLines(INFO_CATEGORIES);
    }

    public static Set<String> filterPartCategories(final Set<String> categories,
                                                   final Set<String> infoCategories) {
        final Set<String> partCategories = new HashSet<>();
        for (final String category : categories) {
            if (isPart(category, infoCategories)) {
                partCategories.add(category);
            }
        }
        return partCategories;
    }

}
