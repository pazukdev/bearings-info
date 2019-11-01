import Vue from 'vue'
import Vuex from 'vuex'
import dictionary from './modules/dictionary'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    plugins: [createPersistedState()],
    modules: {dictionary}
});