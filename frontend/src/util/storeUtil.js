import store from "../plugins/store";

export default {
    setView(view) {
        store.dispatch("setItemView", view);
    },

    setUserData(user) {
        store.dispatch("setUserData", user);
    },

    setAuthorization(authorization) {
        store.dispatch("setAuthorization", authorization);
    },

    userIsBlocked() {
        return store.getters.userData.status === "blocked";
    },

    setEditMode(editMode) {
        store.dispatch("setEditMode", editMode);
    },

    setLoadingState(loadingState) {
        store.dispatch("setLoadingState", loadingState);
    },

    setLoadingStateDefault() {
        this.setLoadingState("Loading");
    },

    setLoadingStateOff() {
        this.setLoadingState("");
    },

    setErrorMessage(message) {
        store.dispatch("setErrorMessage", message);
    },

    setLang(lang) {
        store.dispatch("setLang", lang);
    },

    setLangs(langs) {
        store.dispatch("setLangs", langs);
    },

    setDictionary(dictionary) {
        store.dispatch("setDictionary", dictionary);
    },

    setDictionaryId(dictionaryId) {
        store.dispatch("setDictionaryId", dictionaryId);
    },

    setIncorrectCredentials(incorrectCredentials) {
        store.dispatch("setIncorrectCredentials", incorrectCredentials);
    }

}