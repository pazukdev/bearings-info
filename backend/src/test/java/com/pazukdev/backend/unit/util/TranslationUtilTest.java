package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.util.ItemUtil;
import com.pazukdev.backend.util.TranslatorUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationUtilTest {

    @Test
    public void test3() {
        System.out.println(TranslatorUtil.isInEnglish("abц"));
    }

    @Test
    public void test4() {
        String text = "a b c";
        String textToSearchInDictionary = "b";
        List<String> textAsList = Arrays.asList(text.split("b"));
        String textBefore = "";
        String textAfter = "";

        if (text.contains(" " + textToSearchInDictionary)) {
            textBefore = textAsList.get(0).trim();
        }
        if (text.contains(textToSearchInDictionary + " ")) {
            textAfter = textAsList.get(textAsList.size() - 1).trim();
        }

        System.out.println(textBefore);
        System.out.println(textAfter);
    }

    @Test
    public void test5() {
        System.out.println(new ArrayList<>(Arrays.asList(ItemUtil.SpecialItemId.values())));
    }

    @Test
    public void testParsingOfJsonArrayFromGoogleApi() throws JSONException {
        final String inputJson = "[[[\"नमस्ते\",\"hello\",,,1]],,\"en\"]";
        final JSONArray jsonArray = new JSONArray(inputJson); // [[["नमस्ते","hello",,,1]],,"en"]
        System.out.println(jsonArray);
        final JSONArray jsonArray2 = (JSONArray) jsonArray.get(0); // [["नमस्ते","hello",,,1]]
        System.out.println(jsonArray2);
        final JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0); // ["नमस्ते","hello",,,1]
        System.out.println(jsonArray3);
        System.out.println(jsonArray3.get(0).toString()); // "नमस्ते"
    }

}
