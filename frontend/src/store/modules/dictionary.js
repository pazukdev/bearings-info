
const state = {
    auth: false,
    login: true,
    admin: false,
    loading: false,
    incorrectCredentials: false,
    authorization: "",
    userName: "",
    itemView: "",
    itemIds: []
};

const actions = {
    setAuthorization: ({commit}, context) => {
        commit("setAuthorization", context);
    },

    setIncorrectCredentials: ({commit}, context) => {
        commit("setIncorrectCredentials", context)
    },

    setUserName: ({commit}, context) => {
        commit("setUserName", context);
    },

    setAdmin: ({commit}, context) => {
        commit("setAdmin", context);
    },

    setLoading: ({commit}, context) => {
        commit("setLoading", context);
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
    setAuthorization(state, authorization) {
        state.authorization = authorization;
    },

    setIncorrectCredentials(state, incorrectCredentials) {
        state.incorrectCredentials = incorrectCredentials === true;
    },

    setAdmin(state, admin) {
        state.admin = admin === true;
    },

    setLoading(state, loading) {
        state.loading = loading === true;
    },

    setUserName(state, userName) {
        state.userName = userName;
    },

    setItemView(state, itemView) {
        state.itemView = itemView;
        state.loading = false;
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