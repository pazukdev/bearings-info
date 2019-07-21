import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Login from './components/Login'
import Home from './components/Home'

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes: [
    {path: '/', component: Home},
    {path: '/login', component: Login}
  ]
});

new Vue({
  render: h => h(App),
  router
}).$mount('#app');
