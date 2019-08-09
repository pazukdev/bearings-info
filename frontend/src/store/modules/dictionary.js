
const state = {
    auth: false,
    login: true,
    admin: false,
    userName: "",
    authorization: "",
    incorrectCredentials: false,
    homeComponent: "",
    motorcycles: [],
    bearings: [],
    seals: [],
    users: []
};

const actions = {
    setAuthorization: ({commit}, context) => {
        commit("setAuthorization", context)
    },

    setIncorrectCredentials: ({commit}, context) => {
        commit("setIncorrectCredentials", context)
    },

    setUserName: ({commit}, context) => {
        commit("setUserName", context)
    },

    setAdmin: ({commit}, context) => {
        commit("setAdmin", context)
    },

    setHomeComponent: ({commit}, context) => {
        commit("setHomeComponent", context)
    },

    setMotorcycles: ({commit}, context) => {
        commit("setMotorcycles", context)
    },

    setBearings: ({commit}, context) => {
        commit("setBearings", context)
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
        state.homeComponent = homeComponent;
    },

    setMotorcycles(state, motorcycles) {
        state.motorcycles = motorcycles;
    },

    setBearings(state, bearings) {
        state.bearings = bearings;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}