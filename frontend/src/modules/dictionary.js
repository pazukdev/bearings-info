import shared from "../util/shared";

const state = {
    basicUrl: "backend",
    lang: "en",
    langs: [],
    allowedLangs: ["en", "ru", "uk", "pl", "lt", "lv", "be", "et", "fr", "de", "cs"],
    dictionary: [],
    dictionaryId: "",
    editMode: false,
    loadingState: "",
    incorrectCredentials: false,
    authorization: "",
    userData: {id: 1, name: "guest", role: "GUEST", rating: 0, wishListId: 1},
    itemView: "",
    errorMessage: "",
    countries: []
};

const actions = {
    setBasicUrl: ({commit}, context) => {
        commit("setBasicUrl", context);
    },

    setLang: ({commit}, context) => {
        commit("setLang", context);
    },

    setLangs: ({commit}, context) => {
        commit("setLangs", context);
    },

    setDictionary: ({commit}, context) => {
        commit("setDictionary", context);
    },

    setDictionaryId: ({commit}, context) => {
        commit("setDictionaryId", context);
    },

    setEditMode: ({commit}, context) => {
        commit("setEditMode", context);
    },

    setAuthorization: ({commit}, context) => {
        commit("setAuthorization", context);
    },

    setIncorrectCredentials: ({commit}, context) => {
        commit("setIncorrectCredentials", context)
    },

    setUserData: ({commit}, context) => {
        commit("setUserData", context);
    },

    setLoadingState: ({commit}, context) => {
        commit("setLoadingState", context);
    },

    setItemView: ({commit}, context) => {
        commit("setItemView", context);
    },

    setErrorMessage: ({commit}, context) => {
        commit("setErrorMessage", context);
    },

    setCountries: ({commit}, context) => {
        commit("setCountries", context);
    }
};

const mutations = {

    setLang(state, lang) {
        if (shared.isEmpty(lang)) {
            lang = "en";
        }
        state.lang = lang;
    },

    setLangs(state, langs) {
        state.langs = langs;
    },

    setDictionary(state, dictionary) {
        state.dictionary = dictionary;
    },

    setDictionaryId(state, dictionaryId) {
        state.dictionaryId = dictionaryId;
    },

    setBasicUrl(state, basicUrl) {
        state.basicUrl = basicUrl;
    },

    setEditMode(state, editMode) {
        state.editMode = editMode === true;
    },

    setAuthorization(state, authorization) {
        state.authorization = authorization;
    },

    setIncorrectCredentials(state, incorrectCredentials) {
        state.incorrectCredentials = incorrectCredentials === true;
    },

    setLoadingState(state, loadingState) {
        state.loadingState = loadingState;
    },

    setUserData(state, userData) {
        state.userData = userData;
    },

    setItemView(state, itemView) {
        state.itemView = itemView;
    },

    setErrorMessage(state, message) {
        state.errorMessage = message;
    },

    setCountries(state, countries) {
        state.countries = countries;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}