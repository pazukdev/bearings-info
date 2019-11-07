import VueRouter from 'vue-router';
import Vue from "vue";
import Item from "../components/Item";
import Login from "../components/Login";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        { path: '/', redirect: { path: '/item/id/home' }},
        { path: '/item', redirect: { path: '/item/id/home' }},
        { path: '/item/id', redirect: { path: '/item/id/home' }},
        { path: '/item/id/-1', redirect: { path: '/item/id/items_management' }},
        { path: '/item/id/-2', redirect: { path: '/item/id/home' }},
        { path: '/item/id/-3', redirect: { path: '/item/id/wishlist' }},
        { path: '/item/id/-4', redirect: { path: '/item/id/users' }},
        { path: '/item/id/:item_id', name: 'item', component: Item, meta: { requiresAuth: true }},
        { path: '/login', name: 'login', component: Login }
    ]
});

export default router;

