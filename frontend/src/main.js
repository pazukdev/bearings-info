import Vue from 'vue'
import router from "./plugins/router";
import App from './App.vue'
import store from "./plugins/store";
import {i18n} from "./plugins/i18n";
import VueDetails from 'vue-details';
import vueCountryRegionSelect from 'vue-country-region-select';
import FlagIcon from 'vue-flag-icon';
// import VueFlags from "@growthbunker/vueflags";
import CountryFlag from 'vue-country-flag'

Vue.use(vueCountryRegionSelect);
Vue.use(FlagIcon);
Vue.use(CountryFlag)

Vue.config.productionTip = false;
Vue.component('v-details', VueDetails);

new Vue({
  router,
  store,
  i18n,
  components: { 'v-details': VueDetails },
  render: h => h(App)
}).$mount('#app');
