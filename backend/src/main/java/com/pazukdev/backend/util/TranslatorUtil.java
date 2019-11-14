package com.pazukdev.backend.util;

import com.pazukdev.backend.dto.ItemView;
import com.pazukdev.backend.dto.NestedItemDto;
import com.pazukdev.backend.dto.table.PartsTable;
import com.pazukdev.backend.dto.table.ReplacersTable;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.entity.Item;
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

/**
 * @author Siarhei Sviarkaltsau
 */
public class TranslatorUtil {
    
    private static String EN = "en";

    public static void translate(final String languageFrom,
                                 final String languageTo,
                                 final ItemView itemView,
                                 final boolean addToDictionary) {
        String category = itemView.getCategory();
        TableDto header = itemView.getHeader();
        final PartsTable partsTable = itemView.getPartsTable();
        final ReplacersTable replacersTable = itemView.getReplacersTable();
        final List<NestedItemDto> possibleParts = itemView.getPossibleParts();
        final List<NestedItemDto> replacers = itemView.getReplacers();
        final List<String> categories = itemView.getCategories();

        category = translate(languageFrom, languageTo, category, addToDictionary);
        header = translate(languageFrom, languageTo, header, addToDictionary);
        translate(languageFrom, languageTo, partsTable, addToDictionary);
        translate(languageFrom, languageTo, categories, addToDictionary);

        //itemView.setCategory(category);
        itemView.setHeader(header);
        itemView.setPartsTable(partsTable);
        itemView.setCategories(categories);

        String s = "s";
    }

    public static void translate(final String languageFrom,
                                 final String languageTo,
                                 final Item item,
                                 final boolean addToDictionary) {
        item.setName(translate(languageFrom, languageTo, item.getName(), addToDictionary));
        item.setCategory(translate(languageFrom, languageTo, item.getCategory(), addToDictionary));
    }

    private static TableDto translate(final String languageFrom,
                                     final String languageTo,
                                     final TableDto tableDto,
                                     final boolean addToDictionary) {
        String name = translate(languageFrom, languageTo, tableDto.getName(), addToDictionary);
        final String[][] matrix = tableDto.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            translate(languageFrom, languageTo, matrix[i], addToDictionary);
        }
        return new TableDto(name, matrix);
    }

    private static void translate(final String languageFrom,
                                 final String languageTo,
                                 final PartsTable partsTable,
                                 final boolean addToDictionary) {
        partsTable.setName(translate(languageFrom, languageTo, partsTable.getName(), addToDictionary));
        String[] header = partsTable.getHeader();
        translate(languageFrom, languageTo, header, addToDictionary);
        List<NestedItemDto> dtos = partsTable.getParts();
        translateNestedItemDtoList(languageFrom, languageTo, dtos, addToDictionary);

        for (final PartsTable child : partsTable.getTables()) {
            translate(languageFrom, languageTo, child, addToDictionary);
        }
    }

    private static void translateNestedItemDtoList(final String languageFrom,
                                                  final String languageTo,
                                                  final List<NestedItemDto> dtos,
                                                  final boolean addToDictionary) {
        for (final NestedItemDto dto : dtos) {
            translate(languageFrom, languageTo, dto, addToDictionary);
        }
    }

    private static void translate(final String languageFrom,
                                 final String languageTo,
                                 final NestedItemDto dto,
                                 final boolean addToDictionary) {
        dto.setName(translate(languageFrom, languageTo, dto.getName(), addToDictionary));
        dto.setItemCategory(translate(languageFrom, languageTo, dto.getItemCategory(), addToDictionary));
        dto.setButtonText(translate(languageFrom, languageTo, dto.getButtonText(), addToDictionary));
        dto.setLocation(translate(languageFrom, languageTo, dto.getLocation(), addToDictionary));
    }

    private static void translate(final String languageFrom,
                                 final String languageTo,
                                 final Map<String, String> map,
                                 final boolean addToDictionary) {
        final Map<String, String> copy = new HashMap<>(map);
        map.clear();
        for (final Map.Entry<String, String> entry : copy.entrySet()) {
            final String key = translate(languageFrom, languageTo, entry.getKey(), addToDictionary);
            final String value = translate(languageFrom, languageTo, entry.getValue(), addToDictionary);
            map.put(key, value);
        }
    }

    private static void translate(final String languageFrom,
                                 final String languageTo,
                                 final List<String> list,
                                 final boolean addToDictionary) {
        final List<String> copy = new ArrayList<>(list);
        list.clear();
        for (final String s : copy) {
            list.add(translate(languageFrom, languageTo, s, addToDictionary));
        }
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

    public static String translate(final String languageFrom,
                                   final String languageTo,
                                   final String text,
                                   final boolean addToDictionary) {

        if (text == null) {
            return null;
        }
        if (text.equals("-")) {
            return "-";
        }
        if (text.contains(".png")) {
            return text;
        }
        if (languageFrom.equals(languageTo)) {
            return text;
        }

        if (languageFrom.equals("en")) {
            return translateFromEnglish(languageTo, text);
        } else {
            return translateToEnglish(languageFrom, text, addToDictionary);
        }
    }

    private static String translateFromEnglish(final String languageTo, final String s) {
        String translated = getValueFromDictionary(s, languageTo);
        if (translated != null) {
            return translated;
        } else {
            return s;
        }
    }

    private static String translateToEnglish(final String languageFrom,
                                             final String text,
                                             final boolean addToDictionary) {

        String translated = getValueFromDictionary(text, "en");
        if (translated != null) {
            return translated;
        }

        try {
            translated = translateWithGoogle(languageFrom, "en", text);
            if (translated.equals(text)) {
                return text;
            }
            if (addToDictionary) {
                addToDictionary(text, translated, languageFrom);
            }
        } catch (final IOException e) {
            try {
                translated = transliterate(text);
                if (addToDictionary) {
                    addToDictionary(text, translated, languageFrom);
                }
            } catch (final IOException e1) {
                return text;
            }
        }
        return translated;
    }

    public static boolean isInEnglish(final String text) {
        return !containsCyrillic(text);
    }

    private static boolean containsCyrillic(final String text) {
        for(int i = 0; i < text.length(); i++) {
            if(Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return true;
            }
        }
        return false;
    }

    private static String transliterate(String message){
        char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        StringBuilder builder = new StringBuilder();
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

    private static String getValueFromDictionary(String value, final String language) {
        final Set<String> lines = FileUtil.getTxtFileLines(FileUtil.getDictionaryFilePath());
        for (final String line : lines) {
            if (language.equals("en")) {
                if (line.split(";")[2].equals(value)) {
                    return line.split(";")[1];
                }
            } else {
                if (line.contains(language + ";" + value + ";")) {
                    final String translated = line.split(";")[2];
                    if (translationResultIsBroken(translated)) {
                        return null;
                    }
                    return translated;
                }
            }
        }
        return null;
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
                + ";" + valueInDefaultLanguage
                + ";" + valueInSpecifiedLanguage;
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

}
