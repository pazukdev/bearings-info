package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.table.HeaderTable;
import com.pazukdev.backend.dto.table.HeaderTableRow;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.view.ItemView;
import com.pazukdev.backend.service.ItemService;
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

import static com.pazukdev.backend.util.SpecificStringUtil.*;

/**
 * @author Siarhei Sviarkaltsau
 */
public class TranslatorUtil {

    private static String EN = "en";
    private static String WORD_SEPARATOR = " ";
    private static String DICTIONARY_SEPARATOR = "=";

    public static void translate(final String languageFrom,
                                 final String languageTo,
                                 final ItemView itemView,
                                 final boolean addToDictionary,
                                 final ItemService service) throws Exception {
        HeaderTable header = itemView.getHeader();
        final String category = itemView.getCategory();
        final String localizedName = itemView.getLocalizedName();
        final PartsTable partsTable = itemView.getPartsTable();
        final ReplacersTable replacersTable = itemView.getReplacersTable();
        final List<NestedItemDto> possibleParts = itemView.getPossibleParts();
        final List<NestedItemDto> replacers = itemView.getPossibleReplacers();
        final List<String> categories = itemView.getAllCategories();

        try {
            header = translate(languageFrom, languageTo, header, addToDictionary, service);
            translate(languageFrom, languageTo, partsTable, addToDictionary, service);
            translate(languageFrom, languageTo, replacersTable, addToDictionary, service);
            translateNestedItemDtoList(languageFrom, languageTo, possibleParts, service);
            translateNestedItemDtoList(languageFrom, languageTo, replacers, service);
            translate(languageFrom, languageTo, categories, addToDictionary);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new Exception("Translation is not finished because of error. "
                    + "To remove this message please select English language");
        }

        itemView.setLocalizedCategory(translate(languageFrom, languageTo, category, addToDictionary));
        itemView.setLocalizedName(translate(languageFrom, languageTo, localizedName, addToDictionary));
        itemView.setHeader(header);
        itemView.setPartsTable(partsTable);
        itemView.setReplacersTable(replacersTable);
        itemView.setPossibleParts(possibleParts);
        itemView.setPossibleReplacers(replacers);
        itemView.setAllCategories(categories);
    }

    private static HeaderTable translate(final String languageFrom,
                                         final String languageTo,
                                         final HeaderTable headerTable,
                                         final boolean addToDictionary,
                                         final ItemService itemService) {
//        headerTable.setLocalizedName(translate(languageFrom, languageTo, headerTable.getName(), false, true, itemService));
        for (final HeaderTableRow row : headerTable.getRows()) {
            row.setParameter(translate(languageFrom, languageTo, row.getParameter(), addToDictionary));
            row.setValue(translate(languageFrom, languageTo, row.getValue(), addToDictionary));
        }

        return headerTable;
    }

    public static void translate(final String languageFrom,
                                 final String languageTo,
                                 final PartsTable partsTable,
                                 final boolean addToDictionary,
                                 final ItemService itemService) {
        final String name = partsTable.getName();
        partsTable.setLocalizedName(translate(languageFrom, languageTo, name, addToDictionary));
        String[] header = partsTable.getHeader();
        translate(languageFrom, languageTo, header, addToDictionary);
        List<NestedItemDto> dtos = partsTable.getParts();
        translateNestedItemDtoList(languageFrom, languageTo, dtos, itemService);
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final ReplacersTable replacersTable,
                                  final boolean addToDictionary,
                                  final ItemService itemService) {
        replacersTable.setLocalizedName(translate(languageFrom, languageTo, replacersTable.getName(), addToDictionary));
        final List<NestedItemDto> replacers = replacersTable.getReplacers();
        translateNestedItemDtoList(languageFrom, languageTo, replacers, itemService);
    }

    public static void translateNestedItemDtoList(final String languageFrom,
                                                  final String languageTo,
                                                  final List<NestedItemDto> dtos,
                                                  final ItemService itemService) {
        for (final NestedItemDto dto : dtos) {
            translate(languageFrom, languageTo, dto, itemService);
        }
    }

    private static void translate(final String langFrom,
                                  final String langTo,
                                  final NestedItemDto dto,
                                  final ItemService itemService) {
        dto.translate(langFrom, langTo, itemService);
    }

//    private static void translate(final String languageFrom,
//                                  final String languageTo,
//                                  final Map<String, String> map,
//                                  final boolean addToDictionary,
//                                  final ItemService itemService) {
//        final Map<String, String> copy = new HashMap<>(map);
//        map.clear();
//        for (final Map.Entry<String, String> entry : copy.entrySet()) {
//            final String key = translate(languageFrom, languageTo, entry.getKey(), addToDictionary);
//            final String value = translate(languageFrom, languageTo, entry.getValue(), addToDictionary);
//            map.put(key, value);
//        }
//    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final List<String> list,
                                  final boolean addToDictionary) {
        final List<String> copy = new ArrayList<>(list);
        list.clear();
        for (final String s : copy) {
            list.add(translate(languageFrom, languageTo, s, addToDictionary));
        }
        list.sort(String::compareTo);
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final String[] array,
                                  final boolean addToDictionary) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = translate(languageFrom, languageTo, array[i], addToDictionary);
        }
    }

    public static String translate(final String langFrom,
                                   final String langTo,
                                   String text,
                                   final boolean addToDictionary) {

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
            String translated = getValueFromDictionary(text, langTo);
            if (!isTranslated(translated, text)) {
                translated = parseAndTranslate(langTo, text);
                if (!isTranslated(translated, text)) {
                    return text;
                }
            }
            return translated;
        } else {
            return translateToEnglish(langFrom, text, addToDictionary);
        }
    }

    private static String translateToEnglish(final String langFrom,
                                             String text,
                                             final boolean addToDictionary) {
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
                final String translatedBeforeLastChar = translateToEnglish(langFrom, beforeLastChar, addToDictionary);
                return text.replaceFirst(beforeLastChar, translatedBeforeLastChar);
            }

            if (isName(text)) {
                return text;
            }

            if (startsWithNumber(text)) {
                final String afterNumber = text.replace(getSubstringWithFirstNumber(text), "");
                final String translatedAfterNumber = translateToEnglish(langFrom, afterNumber, addToDictionary);
                return text.replaceFirst(afterNumber, translatedAfterNumber);
            }

            if (isBetweenParenthesises(text)) {
                return "(" + translateToEnglish(langFrom, getStringBetweenParentheses(text), addToDictionary) + ")";
            }
        }

        final String comma = ", ";
        if (text.contains(comma)) {
            final String firstPart = text.split(comma)[0];
            final String secondPart = text.split(comma)[1];
            final String translatedFirstPart = translateToEnglish(langFrom, firstPart, addToDictionary);
            final String translatedSecondPart = translateToEnglish(langFrom, secondPart, addToDictionary);
            return translatedFirstPart + comma + translatedSecondPart;
        }

        String translated = getValueFromDictionary(text, "en");
        if (isTranslated(translated, text)) {
            return translated;
        }

        try {
            translated = translateWithGoogle(langFrom, "en", text).trim();
            if (!isTranslated(translated, text)) {
                return text;
            }
            if (addToDictionary) {
                addToDictionary(text, translated, langFrom);
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

    private static String parseAndTranslate(final String languageTo, String text) {
        final Map<String, String> map = new HashMap<>();
        int i = 0;
        final String s = "#";
        for (final List<String> subList : getAllSubListsSortedBySize(splitIntoWords(text))) {
            final String toTranslate = wordsIntoText(subList);
            final String translated = getValueFromDictionary(toTranslate, languageTo);
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

    private static String transliterate(String message){
        final char[] abcCyr =   {' ','а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т',
                'у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й',
                'К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я'
                ,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
        };
        final String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t",
                "u","f","h","ts","ch","sh","sch","","i","","e","ju","ja",
                "A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch",
                "Sh","Sch","","I","","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
                "P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    private static boolean translationResultIsBroken(final String translationResult) {
        return translationResult.contains("??") || translationResult.contains("? ?");
    }

    private static String getValueFromDictionary(String value, final String lang) {
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
            final String translatedBeforeLastChar = getValueFromDictionary(beforeLastChar, lang);
            return value.replaceFirst(beforeLastChar, translatedBeforeLastChar);
        }

        if (isSingleWord(value)) {
            if (isName(value)) {
                final String beforeNumber = value.split(getSubstringWithFirstNumber(value))[0];
                final String translatedBeforeNumber = getValueFromDictionary(beforeNumber, lang);
                return value.replaceFirst(beforeNumber, translatedBeforeNumber);
            }

            if (startsWithNumber(value)) {
                final String afterNumber = value.replace(getSubstringWithFirstNumber(value), "");
                final String translatedAfterNumber = getValueFromDictionary(afterNumber, lang);
                return value.replaceFirst(afterNumber, translatedAfterNumber);
            }

            if (isBetweenParenthesises(value)) {
                return "(" + getValueFromDictionary(getStringBetweenParentheses(value), lang) + ")";
            }
        }

        String translated = value;
        final Set<String> lines = FileUtil.getTxtFileLines(FileUtil.getDictionaryFilePath());
        for (final String line : lines) {
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
        if (startsWithUppercase) {
            translated = capitalize(translated);
        }
        return translated;
    }

    private static void addToDictionary(final String value,
                                        final String valueInEnglish,
                                        final String language) throws IOException {
        if (language.equals("en")) {
            return;
        }

        final String newDictionaryLine = createDictionaryLine(language, valueInEnglish, value);
        final Path dictionaryPath = FileUtil.getDictionaryFilePath();
        final Set<String> fileContent = FileUtil.getTxtFileLines(dictionaryPath);
        String foundInDictionary = getValueFromDictionary(valueInEnglish, language);
        if (foundInDictionary != null) {
            fileContent.remove(foundInDictionary);
        }
        fileContent.add(newDictionaryLine);
        Files.write(dictionaryPath, getSortedFileLines(fileContent), StandardCharsets.UTF_8);
    }

    private static List<String> getSortedFileLines(final Set<String> fileContent) {
        final List<String> sortedFileContent = new ArrayList<>(fileContent);
        sortedFileContent.sort(String::compareTo);
        return sortedFileContent;
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

}
