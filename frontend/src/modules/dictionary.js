const state = {
    basicUrl: "backend",
    appLanguage: "en",
    editMode: false,
    loadingState: false,
    incorrectCredentials: false,
    itemsManagementId: -1,
    motorcycleCatalogueId: -2,
    wishlistId: -3,
    userlistId: -4,
    authorization: "",
    userName: "",
    itemView: ""
};

const actions = {
    setBasicUrl: ({commit}, context) => {
        commit("setBasicUrl", context);
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

    setLoadingState: ({commit}, context) => {
        commit("setLoadingState", context);
    },

    setItemView: ({commit}, context) => {
        commit("setItemView", context);
    }
};

const mutations = {
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
        state.loadingState = loadingState === true;
    },

    setUserName(state, userName) {
        state.userName = userName;
    },

    setItemView(state, itemView) {
        state.itemView = itemView;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}