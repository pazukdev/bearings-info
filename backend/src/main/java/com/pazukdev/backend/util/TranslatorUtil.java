package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.UserActionDto;
import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.view.ItemView;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.pazukdev.backend.util.CategoryUtil.Parameter;
import static com.pazukdev.backend.util.FileUtil.*;
import static com.pazukdev.backend.util.SpecificStringUtil.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class TranslatorUtil {

    private static String WORD_SEPARATOR = " ";
    private static String DICTIONARY_SEPARATOR = "=";

    public static void translate(final String langFrom,
                                 final String langTo,
                                 final ItemView view,
                                 final boolean addToDictionary) throws Exception {
        final Set<String> dictionary = getDictionary();
        translate(langFrom, langTo, view, addToDictionary, dictionary);
        saveDictionary(dictionary);
    }

    public static void translate(final String langFrom,
                                 final String langTo,
                                 final ItemView view,
                                 final boolean addToDictionary,
                                 final Set<String> dictionary) throws Exception {
        HeaderTable header = view.getHeader();
        final String category = view.getCategory();
        final String localizedName = view.getLocalizedName();
        final ReplacersTable replacersTable = view.getReplacersTable();
        final List<String> categories = view.getAllCategories();

        try {
            header = translate(langFrom, langTo, header, addToDictionary, dictionary);
            translate(langFrom, langTo, replacersTable, addToDictionary, dictionary);
            translate(langFrom, langTo, categories, addToDictionary, dictionary);

            view.setLocalizedCategory(translate(langFrom, langTo, category, addToDictionary, dictionary));
            view.setLocalizedName(translate(langFrom, langTo, localizedName, false, dictionary));
            view.setChildren(translateItemDtoList(langFrom, langTo, view.getChildren(), dictionary));
            view.setAllChildren(translateItemDtoList(langFrom, langTo, view.getAllChildren(), dictionary));
            view.setPossibleParts(translateItemDtoList(langFrom, langTo, view.getPossibleParts(), dictionary));
            view.setPossibleReplacers(translateItemDtoList(langFrom, langTo, view.getPossibleReplacers(), dictionary));
            if (view.getAdminMessage() != null) {
                view.getAdminMessage().translate(langTo, dictionary);
            }
            for (final UserActionDto userAction : view.getLastVehicles()) {
                userAction.translate(langTo, dictionary);
            }
            for (final UserActionDto userAction : view.getLastReplacers()) {
                userAction.translate(langTo, dictionary);
            }

            if (view.getParents() != null) {
                translate(langFrom, langTo, view.getParents(), addToDictionary, dictionary);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            throw new Exception("Translation isn't finished because of the error. "
                    + "To remove this message please select English language");
        }

        view.setHeader(header);
        view.setReplacersTable(replacersTable);
        view.setAllCategories(categories);
    }

    private static HeaderTable translate(final String languageFrom,
                                         final String languageTo,
                                         final HeaderTable headerTable,
                                         final boolean addToDictionary,
                                         final Set<String> dictionary) {
        if (headerTable == null) {
            return null;
        }
        for (final HeaderTableRow row : headerTable.getRows()) {
            if (row.getName().equalsIgnoreCase(Parameter.DescriptionIgnored.NAME)) {
                row.setValue(translate(languageFrom, languageTo, row.getValue(), false, dictionary));
            }
            row.setParameter(translate(languageFrom, languageTo, row.getParameter(), addToDictionary, dictionary));
            row.setValue(translate(languageFrom, languageTo, row.getValue(), addToDictionary, dictionary));
        }

        return headerTable;
    }

    private static void translate(final String langFrom,
                                  final String langTo,
                                  final ReplacersTable replacersTable,
                                  final boolean addToDictionary,
                                  final Set<String> dictionary) {
        if (replacersTable == null) {
            return;
        }
        final String localizedName = translate(langFrom, langTo, replacersTable.getName(), addToDictionary, dictionary);
        replacersTable.setLocalizedName(localizedName);
        final List<NestedItemDto> replacers = replacersTable.getReplacers();
        translateItemDtoList(langFrom, langTo, replacers, dictionary);
    }

    public static List<NestedItemDto> translateItemDtoList(final String langFrom,
                                                           final String langTo,
                                                           final List<NestedItemDto> dtos,
                                                           final Set<String> dictionary) {
        for (final NestedItemDto dto : dtos) {
            dto.translate(langFrom, langTo, dictionary);
        }
        return dtos;
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final List<String> list,
                                  final boolean addToDictionary,
                                  final Set<String> dictionary) {
        final List<String> copy = new ArrayList<>(list);
        list.clear();
        for (final String s : copy) {
            list.add(translate(languageFrom, languageTo, s, addToDictionary, dictionary));
        }
        list.sort(String::compareTo);
    }

    public static String translate(final String langFrom,
                                   final String langTo,
                                   String text,
                                   final boolean addToDictionary,
                                   final Set<String> dictionary) {

        if (text == null) {
            return null;
        }

        text = text.trim();

        if (text.equals("-") || text.equals("no id") || text.equals("") || text.contains(".png")) {
            return text;
        }
        if (langFrom.equals(langTo)) {
            return text;
        }

        if (langFrom.equals("en")) {
            String translated = getValueFromDictionary(text, langTo, dictionary);
            if (!isTranslated(translated, text)) {
                translated = parseAndTranslate(langTo, text, dictionary);
                if (!isTranslated(translated, text)) {
                    return text;
                }
            }
            return translated;
        } else {
            return translateToEnglish(langFrom, text, addToDictionary, dictionary);
        }
    }

    private static String translateToEnglish(final String langFrom,
                                             String text,
                                             final boolean addToDictionary,
                                             final Set<String> dictionary) {
        if (text == null || langFrom == null) {
            return null;
        }
        if (langFrom.equals("ru") && !containsCyrillic(text)) {
            return text;
        }

        text = text.trim();

        if (isEmpty(text)) {
            return text;
        }

        if (isNumber(text) || containsOnlyDigitsAndDash(text)) {
            return text;
        }

        final boolean startsWithUppercase = startsWithUppercase(text);

        if (isSingleWord(text)) {
            if (endChars.contains(getLastChar(text)) && text.length() > 1) {
                final String beforeLastChar = removeLastChar(text);
                final String translatedBeforeLastChar
                        = translateToEnglish(langFrom, beforeLastChar, addToDictionary, dictionary);
                return text.replaceFirst(beforeLastChar, translatedBeforeLastChar);
            }

            if (isName(text)) {
                return text;
            }

            if (startsWithNumber(text)) {
                final String afterNumber = text.replace(getSubstringWithFirstNumber(text), "");
                final String translatedAfterNumber
                        = translateToEnglish(langFrom, afterNumber, addToDictionary, dictionary);
                return text.replaceFirst(afterNumber, translatedAfterNumber);
            }

            if (isBetweenParenthesises(text)) {
                final String stringBetweenParentheses = getStringBetweenParentheses(text);
                return "(" + translateToEnglish(langFrom, stringBetweenParentheses, addToDictionary, dictionary) + ")";
            }
        }

        final String comma = ", ";
        if (text.contains(comma)) {
            final String firstPart = text.split(comma)[0];
            final String secondPart = text.split(comma)[1];
            final String translatedFirstPart = translateToEnglish(langFrom, firstPart, addToDictionary, dictionary);
            final String translatedSecondPart = translateToEnglish(langFrom, secondPart, addToDictionary, dictionary);
            return translatedFirstPart + comma + translatedSecondPart;
        }

        String translated = getValueFromDictionary(text, "en", dictionary);
        if (isTranslated(translated, text)) {
            return translated;
        }

        try {
            translated = translateWithGoogle(langFrom, "en", text).trim();
            if (!isTranslated(translated, text)) {
                return text;
            }
            if (addToDictionary) {
                addToDictionary(text, translated, langFrom, dictionary);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return text;
        }
        if (startsWithUppercase) {
            translated = capitalize(translated);
        }
        return translated;
    }

    public static boolean isTranslated(final String translated, final String original) {
        return translated != null && !translated.equalsIgnoreCase(original);
    }

    private static String parseAndTranslate(final String languageTo, String text, final Set<String> dictionary) {
        final Map<String, String> map = new HashMap<>();
        int i = 0;
        final String s = "#";
        for (final List<String> subList : getAllSubListsSortedBySize(splitIntoWords(text))) {
            final String toTranslate = wordsIntoText(subList);
            final String translated = getValueFromDictionary(toTranslate, languageTo, dictionary);
            if (translated != null && !translated.equalsIgnoreCase(toTranslate)) {
                final String key = s + i++;
                text = text.replace(toTranslate, key);
                map.put(key, translated);
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return text;
    }

    private static List<List<String>> getAllSubListsSortedBySize(final List<String> words) {
        final List<String> subArraysAsStrings = new ArrayList<>();
        for ( int i = 0; i < words.size(); i++) {
            String s = "";
            for (int j = i; j < words.size(); j++) {
                s += words.get(j) + " ";
                subArraysAsStrings.add(s.trim());
            }
        }
        final List<List<String>> subArrays = new ArrayList<>();
        for (final String subArrayAsString : subArraysAsStrings) {
            subArrays.add(Arrays.asList(subArrayAsString.split(WORD_SEPARATOR)));
        }
        subArrays.sort(Comparator.comparing(List::size, Comparator.reverseOrder()));
        return subArrays;
    }

    public static boolean isInEnglish(final String text) {
        return !containsCyrillic(text);
    }

    private static boolean containsCyrillic(final String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return true;
            }
        }
        return false;
    }

    private static boolean translationResultIsBroken(final String translationResult) {
        return translationResult.contains("??") || translationResult.contains("? ?");
    }

    private static String getValueFromDictionary(String value,
                                                 final String lang,
                                                 final Set<String> dictionary) {
        if (value == null) {
            return null;
        }

        value = value.trim();

        if (SpecificStringUtil.isEmpty(value)) {
            return value;
        }

        if (containsOnlyDigitsAndDash(value)) {
            return value;
        }

        final boolean startsWithUppercase = startsWithUppercase(value);

        if (endChars.contains(getLastChar(value)) && value.length() > 1) {
            final String beforeLastChar = removeLastChar(value);
            final String translatedBeforeLastChar = getValueFromDictionary(beforeLastChar, lang, dictionary);
            return value.replaceFirst(beforeLastChar, translatedBeforeLastChar);
        }

        if (value.equals("G-414")) {
            int i = 0;
        }

        if (isSingleWord(value)) {
            if (isName(value)) {
                String translated = find(value, lang, dictionary);
                if (isFound(value, translated)) {
                    if (startsWithUppercase) {
                        translated = capitalize(translated);
                    }
                    return translated;
                }
                try {
                    final String beforeNumber = value.split(getSubstringWithFirstNumber(value))[0];
                    final String translatedBeforeNumber = getValueFromDictionary(beforeNumber, lang, dictionary);
                    return value.replaceFirst(beforeNumber, translatedBeforeNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (startsWithNumber(value)) {
                final String afterNumber = value.replace(getSubstringWithFirstNumber(value), "");
                final String translatedAfterNumber = getValueFromDictionary(afterNumber, lang, dictionary);
                return value.replaceFirst(afterNumber, translatedAfterNumber);
            }

            if (isBetweenParenthesises(value)) {
                return "(" + getValueFromDictionary(getStringBetweenParentheses(value), lang, dictionary) + ")";
            }
        }

        String translated = find(value, lang, dictionary);
        if (startsWithUppercase) {
            translated = capitalize(translated);
        }
        return translated;
    }

    private static String find(final String value, final String lang, final Set<String> dictionary) {
        String translated = value;
        for (final String line : dictionary) {
            if (line.split(DICTIONARY_SEPARATOR).length < 3) {
                continue;
            }
            if (lang.equals("en")) {
                if (line.split(DICTIONARY_SEPARATOR)[2].equalsIgnoreCase(value)) {
                    translated = line.split(DICTIONARY_SEPARATOR)[1];
                    break;
                }
            } else {
                if (line.toLowerCase()
                        .contains(lang + DICTIONARY_SEPARATOR + value.toLowerCase() + DICTIONARY_SEPARATOR)) {
                    translated = line.split(DICTIONARY_SEPARATOR)[2];
                    if (translationResultIsBroken(translated)) {
                        return value;
                    }
                    break;
                }
            }
        }
        return translated;
    }

    private static boolean isFound(final String input, final String output) {
        return !input.equalsIgnoreCase(output);
    }

    private static void addToDictionary(final String value,
                                        final String valueInEnglish,
                                        final String language,
                                        final Set<String> dictionary) {
        if (language.equals("en")) {
            return;
        }

        final String newDictionaryLine = createDictionaryLine(language, valueInEnglish, value);
        String foundInDictionary = getValueFromDictionary(valueInEnglish, language, dictionary);
        if (foundInDictionary != null) {
            dictionary.remove(foundInDictionary);
        }
        dictionary.add(newDictionaryLine);
    }

    private static String createDictionaryLine(final String lang,
                                               final String valueInEnglish,
                                               final String value) {
        return lang + DICTIONARY_SEPARATOR + valueInEnglish + DICTIONARY_SEPARATOR + value;
    }

    private static String translateWithGoogle(final String languageFrom,
                                              final String languageTo,
                                              final String s) throws IOException {

        final String urlString = getUrlString(languageFrom, languageTo, s);

        final URL url = new URL(urlString);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        final StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return getTranslation(response.toString());
    }

    private static String getUrlString(final String sourceLanguage,
                                       final String toLanguage,
                                       final String stringToTranslate) throws UnsupportedEncodingException {
        return "https://translate.googleapis.com/translate_a/single?client=gtx&" +
                "sl=" + sourceLanguage +
                "&tl=" + toLanguage +
                "&dt=t&q=" + URLEncoder.encode(stringToTranslate, "UTF-8");
    }

    private static String getTranslation(final String inputJson) {
        final JSONArray jsonArray = new JSONArray(inputJson);
        final JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        final JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
        return jsonArray3.get(0).toString();
    }

    public static Set<String> getDictionary() {
        return getTxtFileLines(getDictionaryFilePath());
    }

    public static void saveDictionary(final Set<String> dictionary) {
        try {
            Files.write(getDictionaryFilePath(), getSortedFileLines(dictionary), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getDictionaryFilePath() {
        return getTxtFilePath(FileName.DICTIONARY);
    }

}
