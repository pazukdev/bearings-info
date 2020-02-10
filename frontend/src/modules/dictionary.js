const state = {
    basicUrl: "backend",
    langs: [],
    appLanguage: "en",
    editMode: false,
    loadingState: "",
    incorrectCredentials: false,
    authorization: "",
    userData: "",
    itemView: "",
    errorMessage: ""
};

const actions = {
    setBasicUrl: ({commit}, context) => {
        commit("setBasicUrl", context);
    },

    setLangs: ({commit}, context) => {
        commit("setLangs", context);
    },

    setAppLanguage: ({commit}, context) => {
        commit("setAppLanguage", context);
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

    setUserName: ({commit}, context) => {
        commit("setUserName", context);
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
    setLangs(state, langs) {
        state.langs = langs;
    },

    setAppLanguage(state, appLanguage) {
        state.appLanguage = appLanguage;
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

    setUserName(state, userName) {
        state.userName = userName;
    },

    setUserStatus(state, userStatus) {
        state.userStatus = userStatus;
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