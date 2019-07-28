import VueRouter from 'vue-router';
import Vue from "vue";
import Home from "../components/Home";
import Login from "../components/Login";
import store from "../store";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {path: '/', name: 'home', component: Home, meta: { requiresAuth: true }},
        {path: '/login', name: 'login', component: Login}
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (store.state.authorization === "") {
            next("/login");
        } else {
            next()
        }
    } else {
        next()
    }
});

export default router;

