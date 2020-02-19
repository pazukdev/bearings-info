import shared from "./shared";
import stringUtil from "./stringUtil";
import store from "../plugins/store";

export default {

    translate(text) {
        let lang = store.getters.appLanguage;
        if (lang === "en") {
            return text;
        }
        return this.translateText(text, lang, store.getters.dictionary);
    },

    translateText(text, lang, dictionary) {
        if (lang === "en") {
            return text;
        }
        if (shared.isEmpty(text) || shared.isEmpty(dictionary)) {
            return text;
        }
        for (let i = 0; i < dictionary.length; i++) {
            let line = dictionary[i];
            if (shared.isEmpty(line)) {
                continue;
            }
            let values = line.split("=");
            if (values.length < 3) {
                continue;
            }
            if (values[1].trim().toLowerCase() === text.trim().toLowerCase()) {
                if (stringUtil.isCapitalized(text.trim())) {
                    return stringUtil.capitalize(values[2]);
                }
                return values[2];
            }
        }
        return text;
    }

}