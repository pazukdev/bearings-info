import Vue from 'vue'
import router from "./plugins/router";
import App from './App.vue'
import store from "./plugins/store";
import {i18n} from "./plugins/i18n";
import VueDetails from 'vue-details';
import vueCountryRegionSelect from 'vue-country-region-select'

Vue.use(vueCountryRegionSelect)

Vue.config.productionTip = false;
Vue.component('v-details', VueDetails);

new Vue({
  router,
  store,
  i18n,
  vueCountryRegionSelect,
  components: { 'v-details': VueDetails },
  render: h => h(App)
}).$mount('#app');
