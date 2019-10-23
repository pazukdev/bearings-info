
const state = {
    auth: false,
    login: true,
    admin: false,
    loading: false,
    userName: "",
    authorization: "",
    incorrectCredentials: false,
    motorcycles: [],
    bearings: [],
    seals: [],
    users: [],
    wishList: "",
    table: "",
    itemViews: [],
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

    addItemView: ({commit}, context) => {
        commit("addItemView", context);
    },

    removeLastItemView: ({commit}, context) => {
        commit("removeLastItemView", context);
    },

    removePreLastItemView: ({commit}, context) => {
        commit("removePreLastItemView", context);
    },

    addItemId: ({commit}, context) => {
        commit("addItemId", context);
    },

    removeLastItemId: ({commit}, context) => {
        commit("removeLastItemId", context);
    },

    clearHistory: ({commit}, context) => {
        commit("clearHistory", context);
    },

    setMotorcycles: ({commit}, context) => {
        commit("setMotorcycles", context);
    },

    setBearings: ({commit}, context) => {
        commit("setBearings", context);
    },

    setSeals: ({commit}, context) => {
        commit("setSeals", context);
    },

    setUsers: ({commit}, context) => {
        commit("setUsers", context);
    },

    setWishList: ({commit}, context) => {
        commit("setWishList", context);
    },

    setTable: ({commit}, context) => {
        commit("setTable", context);
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
        if (state.loading !== loading) {
            state.loading = loading === true;
        }
    },

    setUserName(state, userName) {
        state.userName = userName;
    },

    addItemView(state, itemView) {
        state.itemViews.push(itemView);
        state.loading = false;
    },

    removeLastItemView(state) {
        state.loading = true;
        state.itemViews.splice(state.itemViews.length - 1, 1);
    },

    removePreLastItemView(state) {
        state.loading = true;
        state.itemViews.splice(state.itemViews.length - 2, 1);
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