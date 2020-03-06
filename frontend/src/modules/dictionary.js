import shared from "../util/shared";

const state = {
    basicUrl: "backend",
    lang: "en",
    langs: [],
    allowedLangs: ["en", "ru", "ua", "pl", "lt", "lv", "be", "ee", "fr", "de", "cz"],
    dictionary: [],
    editMode: false,
    loadingState: "",
    incorrectCredentials: false,
    authorization: "",
    userData: {id: 1, name: "guest", role: "GUEST", rating: 0, wishListId: 1},
    itemView: "",
    errorMessage: ""
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
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}