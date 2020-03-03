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

        authorization: state => {
            return state.dictionary.authorization;
        },

        basicUrl: state => {
            return state.dictionary.basicUrl;
        },

        lang: state => {
            return state.dictionary.lang;
        },

        dictionary: state => {
            return state.dictionary.dictionary;
        }
    }
});