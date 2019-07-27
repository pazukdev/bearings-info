
const state = {
    auth: false,
    login: true,
    admin: "",
    user: "",
    authorizationHeaderData: ""
};

const actions = {
    setAuthorizationHeaderData: ({commit}, context) => {
        commit("setAuthorizationHeaderData", context)
    }
};

const mutations = {
    setAuthorizationHeaderData(state, data) {
        state.authorizationHeaderData = data;
    }
};

export default {
    namespaced: false,
    state,
    actions,
    mutations
}