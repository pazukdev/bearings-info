import Vue from 'vue'
import router from "./router";
import App from './App.vue'
import store from "./store";
import {i18n} from "./plugins/i18n";
import VueDetails from 'vue-details';

Vue.config.productionTip = false;
Vue.component('v-details', VueDetails);

new Vue({
  router,
  store,
  i18n,
  components: { 'v-details': VueDetails },
  render: h => h(App)
}).$mount('#app');
