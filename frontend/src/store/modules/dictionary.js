
const state = {
    auth: false,
    login: true,
    admin: false,
    userName: "",
    authorization: "",
    incorrectCredentials: false,
    homeComponent: ""
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
        state.userName = userName;;
    },

    setHomeComponent(state, homeComponent) {
        state.homeComponent = homeComponent;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}