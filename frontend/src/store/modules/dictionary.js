
const state = {
    auth: false,
    login: true,
    admin: false,
    userName: "",
    authorization: "",
    incorrectCredentials: false,
    homeComponent: [],
    motorcycles: [],
    bearings: [],
    seals: [],
    users: [],
    wishList: "",
    table: "",
    itemViews: []
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

    setHomeComponent: ({commit}, context) => {
        commit("setHomeComponent", context);
    },

    removeLastComponent: ({commit}, context) => {
        commit("removeLastComponent", context);
    },

    addItemView: ({commit}, context) => {
        commit("addItemView", context);
    },

    removeLastItemView: ({commit}, context) => {
        commit("removeLastItemView", context);
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

    setUserName(state, userName) {
        state.userName = userName;
    },

    setHomeComponent(state, homeComponent) {
        state.homeComponent.push(homeComponent);
    },

    removeLastComponent(state) {
        state.homeComponent.splice(state.homeComponent.length - 1, 1);
    },

    addItemView(state, itemView) {
        state.itemViews.push(itemView);
    },

    removeLastItemView(state) {
        state.itemViews.splice(state.itemViews.length - 1, 1);
    },

    clearHistory(state) {
        state.homeComponent = [];
    },

    setMotorcycles(state, motorcycles) {
        state.motorcycles = motorcycles;
    },

    setBearings(state, bearings) {
        state.bearings = bearings;
    },

    setSeals(state, seals) {
        state.seals = seals;
    },

    setUsers(state, users) {
        state.users = users;
    },

    setWishList(state, wishList) {
        state.wishList = wishList;
    },

    setTable(state, table) {
        state.table = table;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}