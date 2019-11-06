const state = {
    basicUrl: "backend",
    loadingState: false,
    incorrectCredentials: false,
    itemsManagementId: -1,
    motorcycleCatalogueId: -2,
    wishlist: -3,
    userListId: -4,
    authorization: "",
    userName: "",
    itemView: ""
};

const actions = {
    setDefaultState: ({commit}, context) => {
        commit("setDefaultState", context);
    },

    setBasicUrl: ({commit}, context) => {
        commit("setBasicUrl", context);
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
    setDefaultState(state) {
        state.basicUrl = "backend";
        state.loadingState = false;
        state.incorrectCredentials = false;
        state.authorization = "";
        state.userName = "";
        state.itemView = "";
    },

    setBasicUrl(state, basicUrl) {
        state.basicUrl = basicUrl;
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