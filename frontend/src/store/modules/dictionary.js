const state = {
    basicUrl: "backend",
    loadingState: false,
    incorrectCredentials: false,
    authorization: "",
    userName: "",
    itemView: "",
    itemIds: []
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
    },

    addItemId: ({commit}, context) => {
        commit("addItemId", context);
    },

    removeLastItemId: ({commit}, context) => {
        commit("removeLastItemId", context);
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
        state.itemIds = [];
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
    },

    addItemId(state, itemId) {
        if (state.itemIds[state.itemIds.length - 1] !== itemId) {
            state.itemIds.push(itemId);
        }

    },

    removeLastItemId(state) {
        state.itemIds.splice(state.itemIds.length - 1, 1);
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}