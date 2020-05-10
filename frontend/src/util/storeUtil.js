import store from "../plugins/store";

export default {
    setView(view) {
        store.dispatch("setItemView", view);
    },

    setUserData(userData) {
        store.dispatch("setUserData", userData);
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
        let millisecondsToWait = 20000;
        console.log(loadingState);
        setTimeout(function() {
            if (store.getters.loadingState !== "") {
                store.dispatch("setLoadingState", "");
                console.log("loading state turned off automatically");
            }
        }, millisecondsToWait);

    },

    setLoadingStateLoading() {
        this.setLoadingState("Loading");
    },

    setLoadingStateCreating() {
        this.setLoadingState("Creating");
    },

    setLoadingStateDefault() {
        this.setLoadingState("Loading");
    },

    setLoadingStateOff() {
        this.setLoadingState("");
        console.log("loading state turned off");
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