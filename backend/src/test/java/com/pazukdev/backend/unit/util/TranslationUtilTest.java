package com.pazukdev.backend.unit.util;

import com.pazukdev.backend.util.TranslatorUtil;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

public class TranslationUtilTest {

    @Test
    public void test() throws IOException {
        String s = null;
//        s = TranslatorUtil.translate("ru", "en", "Стрекоза",true);
        System.out.println(s);
//        s = TranslatorUtil.translate("en", "en", "Rat", true);
//        System.out.println(s);
//        s = TranslatorUtil.translate("en", "ru", "Rat", true);
//        System.out.println(s);
    }

    @Test
    public void test2() {
        LanguageDetector detector = new OptimaizeLangDetector().loadModels();
        LanguageResult result = detector.detect("суслик");
        System.out.println(result.getLanguage());
    }

    @Test
    public void test3() {
        System.out.println(TranslatorUtil.isInEnglish("abц"));
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
