import VueCookies from 'vue-cookies';
import axios from 'axios';

const state = {
    auth: false,
    login: true,
    admin: "",
    user: ""
};

const getters = {};

const mutations = {
    init(state, dictionaryData) {
        state.auth = true;

        let authorizationToken = VueCookies.get('authorization');
        const arrayOfStrings = authorizationToken.split(".");
        const idBase64 = arrayOfStrings[1];

        let tokenData = JSON.parse(atob(idBase64));
        const tokenSub = tokenData.sub;

        let strings = tokenSub.split(":");
        state.userId = strings[1];
        state.userRole = strings[2];

        state.login = false;
        switch (state.userRole) {
            case("USER"): {
                state.user = true;
                break;
            }
            case("ADMIN"): {
                state.admin = true;
                break;
            }
        }

        axios.get('/backend/user/' + state.userId, {
            headers: {
                Authorization: authorizationToken
            }
        });
    }
};

export default {
    namespaced: true,
    state,
    getters,
    mutations
}