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
                                 final ItemService service) {
        HeaderTable header = itemView.getHeader();
        final String category = itemView.getCategory();
        final String localizedName = itemView.getLocalizedName();
        final PartsTable partsTable = itemView.getPartsTable();
        final ReplacersTable replacersTable = itemView.getReplacersTable();
        final List<NestedItemDto> possibleParts = itemView.getPossibleParts();
        final List<NestedItemDto> replacers = itemView.getPossibleReplacers();
        final List<String> categories = itemView.getAllCategories();

        header = translate(languageFrom, languageTo, header, addToDictionary, service);
        translate(languageFrom, languageTo, partsTable, addToDictionary, service);
        translate(languageFrom, languageTo, replacersTable, addToDictionary, service);
        translateNestedItemDtoList(languageFrom, languageTo, possibleParts, addToDictionary, service);
        translateNestedItemDtoList(languageFrom, languageTo, replacers, addToDictionary, service);
        translate(languageFrom, languageTo, categories, addToDictionary, service);

        itemView.setLocalizedCategory(translate(languageFrom, languageTo, category, addToDictionary, false, service));
        itemView.setLocalizedName(translate(languageFrom, languageTo, localizedName, addToDictionary, false, service));
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
            row.setParameter(translate(languageFrom, languageTo, row.getParameter(), addToDictionary, false, itemService));
            row.setValue(translate(languageFrom, languageTo, row.getValue(), addToDictionary, false, itemService));
        }

        return headerTable;
    }

    public static void translate(final String languageFrom,
                                 final String languageTo,
                                 final PartsTable partsTable,
                                 final boolean addToDictionary,
                                 final ItemService itemService) {
        final String name = partsTable.getName();
        partsTable.setLocalizedName(translate(languageFrom, languageTo, name, addToDictionary, false, itemService));
        String[] header = partsTable.getHeader();
        translate(languageFrom, languageTo, header, addToDictionary, itemService);
        List<NestedItemDto> dtos = partsTable.getParts();
        translateNestedItemDtoList(languageFrom, languageTo, dtos, addToDictionary, itemService);
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final ReplacersTable replacersTable,
                                  final boolean addToDictionary,
                                  final ItemService itemService) {
        replacersTable.setLocalizedName(translate(languageFrom, languageTo, replacersTable.getName(), addToDictionary, false, itemService));
        final List<NestedItemDto> replacers = replacersTable.getReplacers();
        translateNestedItemDtoList(languageFrom, languageTo, replacers, addToDictionary, itemService);
    }

    public static void translateNestedItemDtoList(final String languageFrom,
                                                  final String languageTo,
                                                  final List<NestedItemDto> dtos,
                                                  final boolean addToDictionary,
                                                  final ItemService itemService) {
        for (final NestedItemDto dto : dtos) {
            translate(languageFrom, languageTo, dto, itemService);
        }
    }

    private static void translate(final String langFrom,
                                  final String langTo,
                                  final NestedItemDto dto,
                                  final ItemService itemService) {
//        final String buttonText = dto.getButtonText();
//        final String selectText = dto.getSelectText();
//        final String comment = dto.getComment();
//        final String secondComment = dto.getSecondComment();
//
//        dto.setLocalizedButtonText(translate(langFrom, langTo, buttonText, addToDictionary, true, itemService));
//        dto.setLocalizedSelectText(translate(langFrom, langTo, selectText, addToDictionary, true, itemService));
//        dto.setLocalizedComment(translate(langFrom, langTo, comment, addToDictionary, true, itemService));
//        dto.setLocalizedSecondComment(translate(langFrom, langTo, secondComment, addToDictionary, false, itemService));

        dto.translate(langFrom, langTo, itemService);

    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final Map<String, String> map,
                                  final boolean addToDictionary,
                                  final ItemService itemService) {
        final Map<String, String> copy = new HashMap<>(map);
        map.clear();
        for (final Map.Entry<String, String> entry : copy.entrySet()) {
            final String key = translate(languageFrom, languageTo, entry.getKey(), addToDictionary, false, itemService);
            final String value = translate(languageFrom, languageTo, entry.getValue(), addToDictionary, false, itemService);
            map.put(key, value);
        }
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final List<String> list,
                                  final boolean addToDictionary,
                                  final ItemService itemService) {
        final List<String> copy = new ArrayList<>(list);
        list.clear();
        for (final String s : copy) {
            list.add(translate(languageFrom, languageTo, s, addToDictionary, false, itemService));
        }
        list.sort(String::compareTo);
    }

    private static void translate(final String languageFrom,
                                  final String languageTo,
                                  final String[] array,
                                  final boolean addToDictionary,
                                  final ItemService itemService) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = translate(languageFrom, languageTo, array[i], addToDictionary, false, itemService);
        }
    }

    public static String translate(final String languageFrom,
                                   final String languageTo,
                                   String text,
                                   final boolean addToDictionary,
                                   boolean parseBeforeTranslate,
                                   final ItemService itemService) {

        if (text == null) {
            return null;
        }

        text = text.trim();

        if (text.equals("-") || text.equals("no id") || text.equals("") || text.contains(".png")) {
            return text;
        }
        if (languageFrom.equals(languageTo)) {
            return text;
        }

        if (languageFrom.equals("en")) {
            String translated = getValueFromDictionary(text, languageTo);
            if (translated != null) {
                return translated;
            }
            translated = parseAndTranslate(languageTo, text, itemService);
            return translated != null ? translated : text;
        } else {
            return translateToEnglish(languageFrom, text, addToDictionary);
        }
    }

    public static void main(String[] args) {
        String s = "Большой Слон-1";
        System.out.println(translateToEnglish("ru", s, true));
    }

    private static String translateToEnglish(final String languageFrom,
                                             String text,
                                             final boolean addToDictionary) {
        if (text == null || languageFrom == null) {
            return null;
        }
        if (languageFrom.equals("ru") && !containsCyrillic(text)) {
            return text;
        }

        text = text.trim();

        if (isNumber(text)) {
            return text;
        }

//        if (endChars.contains(getLastChar(value))) {
//            final String beforeLastChar = removeLastChar(value);
//            final String translatedBeforeLastChar = getValueFromDictionary(beforeLastChar, lang);
//            return value.replaceFirst(beforeLastChar, translatedBeforeLastChar);
//        }
//
//        if (isName(value)) {
//            final String beforeNumber = value.split(getSubstringWithFirstNumber(value))[0];
//            final String translatedBeforeNumber = getValueFromDictionary(beforeNumber, lang);
//            return value.replaceFirst(beforeNumber, translatedBeforeNumber);
//        }
//
//        if (startsWithNumber(value)) {
//            final String afterNumber = value.replace(getSubstringWithFirstNumber(value), "");
//            final String translatedAfterNumber = getValueFromDictionary(afterNumber, lang);
//            return value.replaceFirst(afterNumber, translatedAfterNumber);
//        }

        String translated = getValueFromDictionary(text, "en");
        if (translated != null) {
            return translated;
        }

        try {
            translated = translateWithGoogle(languageFrom, "en", text).trim();
            if (translated.equals(text)) {
                return text;
            }
            if (addToDictionary) {
                addToDictionary(text, translated, languageFrom);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return text;
        }
        return translated;
    }

    private static String parseAndTranslate(final String languageTo, String text, final ItemService service) {
        final String empty = null;

        if (false) { // parsing is turned off
            return empty;
        }

        final Map<String, String> map = new HashMap<>();
        int i = 0;
        final String s = "#";
        for (final List<String> subList : getAllSubListsSortedBySize(splitIntoWords(text))) {
            final String toTranslate = wordsIntoText(subList);
            final String translated = getValueFromDictionary(toTranslate, languageTo);
            if (translated != null) {
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

//    public static String translateToEnglish(final String languageFrom, final ItemData itemData) {
//        String nameInEnglish = itemData.getName();
//        if (nameInEnglish != null) {
//            return nameInEnglish;
//        } else {
//            final String localizedName = itemData.getLocalizedName();
//            nameInEnglish = getValueFromDictionary(localizedName, languageFrom);
//            if (nameInEnglish == null) {
//                try {
//                    nameInEnglish = translateWithGoogle(languageFrom, "en", localizedName);
//                    addToDictionary(localizedName, nameInEnglish, languageFrom);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return nameInEnglish;
//    }

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
        if (containsCyrillic(value)) {
            return value;
        }

        value = value.trim();

        if (endChars.contains(getLastChar(value))) {
            final String beforeLastChar = removeLastChar(value);
            final String translatedBeforeLastChar = getValueFromDictionary(beforeLastChar, lang);
            return value.replaceFirst(beforeLastChar, translatedBeforeLastChar);
        }

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

        String translated = null;
        final Set<String> lines = FileUtil.getTxtFileLines(FileUtil.getDictionaryFilePath());
        for (final String line : lines) {
            if (line.split(DICTIONARY_SEPARATOR).length < 3) {
                continue;
            }
            if (lang.equals("en")) {
                if (line.split(DICTIONARY_SEPARATOR)[2].equals(value)) {
                    translated = line.split(DICTIONARY_SEPARATOR)[1];
                    break;
                }
            } else {
                if (line.contains(lang + DICTIONARY_SEPARATOR + value + DICTIONARY_SEPARATOR)) {
                    translated = line.split(DICTIONARY_SEPARATOR)[2];
                    if (translationResultIsBroken(translated)) {
                        return null;
                    }
                    break;
                }
            }
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

    private static String createDictionaryLine(final String languageCode,
                                               final String valueInDefaultLanguage,
                                               final String valueInSpecifiedLanguage) {
        return languageCode
                + DICTIONARY_SEPARATOR + valueInDefaultLanguage
                + DICTIONARY_SEPARATOR + valueInSpecifiedLanguage;
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
        final JSONArray jsonArray = new JSONArray(inputJson); // [[["नमस्ते","hello",,,1]],,"en"]
        final JSONArray jsonArray2 = (JSONArray) jsonArray.get(0); // [["नमस्ते","hello",,,1]]
        final JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0); // ["नमस्ते","hello",,,1]
        return jsonArray3.get(0).toString(); // "नमस्ते"
    }

//    public static int getDictionarySize() {
//
//    }

}
