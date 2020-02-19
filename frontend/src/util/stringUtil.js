import shared from "./shared";

export default {

    capitalize(s) {
        return s[0].toUpperCase() + s.slice(1);
    },

    isCapitalized(text) {
        if (shared.isEmpty(text)) {
            return false;
        }
        return text[0] !== text[0].toLowerCase();
    }

}