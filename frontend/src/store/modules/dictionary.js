
const state = {
    auth: false,
    login: true,
    admin: "",
    user: "",
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