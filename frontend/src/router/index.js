import VueRouter from 'vue-router';
import Vue from "vue";
import Item from "../components/Item";
import Login from "../components/Login";

Vue.use(VueRouter);

const router = new VueRouter({
    routes: [
        {path: '/item/:item_id', name: 'item', component: Item, meta: {requiresAuth: false}},
        {path: '/login', name: 'login', component: Login}
    ]
});

export default router;

