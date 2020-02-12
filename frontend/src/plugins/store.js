import Vue from 'vue'
import Vuex from 'vuex'
import dictionary from '../modules/dictionary'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    plugins: [createPersistedState()],
    modules: {dictionary},
    getters: {
        userData: state => {
            return state.dictionary.userData;
        },

        itemView: state => {
            return state.dictionary.itemView;
        },

        appLanguage: state => {
            return state.dictionary.appLanguage;
        },

        authorization: state => {
            return state.dictionary.authorization;
        },

        basicUrl: state => {
            return state.dictionary.basicUrl;
        },

        dictionary: state => {
            return state.dictionary.dictionary;
        }
    }
});