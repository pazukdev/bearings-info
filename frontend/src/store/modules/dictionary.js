
const state = {
    auth: false,
    login: true,
    admin: "",
    user: "",
    authorization: "",
    incorrectCredentials: false
};

const actions = {
    setAuthorization: ({commit}, context) => {
        commit("setAuthorization", context)
    },

    setIncorrectCredentials: ({commit}, context) => {
        commit("setIncorrectCredentials", context)
    }
};

const mutations = {
    setAuthorization(state, authorization) {
        state.authorization = authorization;
    },

    setIncorrectCredentials(state, incorrectCredentials) {
        state.incorrectCredentials = incorrectCredentials === true;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}