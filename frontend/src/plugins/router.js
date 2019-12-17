import VueRouter from 'vue-router';
import Vue from "vue";
import Home from "../components/Home";
import Item from "../components/Item";
import Login from "../components/Login";
import ItemsManagement from "../components/ItemsManagement";
import store from "./store";

Vue.use(VueRouter);

const router = new VueRouter({

    routes: [
        // possible not valid urls
        { path: '/', redirect: { path: '/home/en' }},
        { path: '/home', redirect: { path: '/home/en' }},
        // redirect to named urls
        { path: '/item/id/-1/en', redirect: { path: '/item/id/items_management/en' }},
        { path: '/item/id/-2/en', redirect: { path: '/item/id/home/en' }},
        { path: '/item/id/-3/en', redirect: { path: '/item/id/wishlist/en' }},
        { path: '/item/id/-4/en', redirect: { path: '/item/id/users/en' }},
        // urls to components binding
        { path: '/home/:lang', name: 'home', component: Home, meta: { requiresAuth: true }},
        { path: '/item/id/:item_id/:lang', name: 'item', component: Item, meta: { requiresAuth: true }},
        { path: '/login/:lang', name: 'login', component: Login },
        { path: '/items_management/:lang', name: 'items_management', component: ItemsManagement, meta: { requiresAuth: true }},
    ]
});

router.beforeResolve((to, from, next) => {
    store.dispatch("setLoadingState", true);
    next();
});

router.afterEach((to, from) => {
});

export default router;

