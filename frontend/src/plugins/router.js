import VueRouter from 'vue-router';
import Vue from "vue";
import Home from "../components/router/Home";
import User from "../components/User";
import Item from "../components/router/Item";
import Login from "../components/router/Login";
import ItemsManagement from "../components/router/ItemsManagement";
import store from "./store";
import UserList from "../components/list/UserList";
import WishList from "../components/list/WishList";

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
        { path: '/login/:lang', name: 'login', component: Login },
        { path: '/home/:lang', name: 'home', component: Home, meta: { requiresAuth: true }},
        { path: '/item/id/:id/:lang', name: 'item', component: Item, meta: { requiresAuth: true }},
        { path: '/user/id/:id/:lang', name: 'user', component: User, meta: { requiresAuth: true }},
        { path: '/user_list/:lang', name: 'user_list', component: UserList, meta: { requiresAuth: true }},
        { path: '/wish_list/:lang', name: 'wish_list', component: WishList, meta: { requiresAuth: true }},
        {
            path: '/items_management/:lang',
            name: 'items_management',
            component: ItemsManagement,
            meta: { requiresAuth: true }
        }
    ]
});

router.beforeResolve((to, from, next) => {
    store.dispatch("setLoadingState", true);
    next();
});

router.afterEach((to, from) => {
});

export default router;

