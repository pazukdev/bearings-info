import VueRouter from 'vue-router';
import Vue from "vue";
import Item from "../components/Item";
import Login from "../components/Login";
import store from "../store";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {path: '/', name: 'item', component: Item, meta: { requiresAuth: true }},
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

