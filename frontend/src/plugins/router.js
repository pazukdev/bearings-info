import VueRouter from 'vue-router';
import Vue from "vue";
import Home from "../components/router/Home";
import User from "../components/router/User";
import Item from "../components/router/Item";
import Login from "../components/router/Login";
import ItemsManagement from "../components/router/ItemsManagement";
import UserList from "../components/list/UserList";
import WishList from "../components/list/WishList";
import storeUtil from "../util/storeUtil";
import Menu from "../components/router/Menu";
import PrivacyPolicy from "../components/info/PrivacyPolicy";
import UserAgreement from "../components/info/UserAgreement";
import Disclaimer from "../components/info/Disclaimer";
import AccountActivation from "../components/router/AccountActivation";
import WhatsNew from "../components/info/WhatsNew";
import InfoForSupporters from "@/components/router/InfoForSupporters";

Vue.use(VueRouter);

const router = new VueRouter({

    routes: [
        // possible not valid urls
        { path: '/', redirect: { path: '/home/en' }},
        { path: '/home', redirect: { path: '/home/en' }},
        // urls to components binding
        { path: '/login/:lang', name: 'login', component: Login },
        { path: '/user-agreement/:lang', name: 'user_agreement', component: UserAgreement },
        { path: '/privacy-policy/:lang', name: 'privacy_policy', component: PrivacyPolicy },
        { path: '/disclaimer/:lang', name: 'disclaimer', component: Disclaimer },
        { path: '/whats_new/:lang', name: 'whats_new', component: WhatsNew },
        { path: '/home/:lang/:message?', name: 'home', component: Home, meta: { requiresAuth: true }},
        { path: '/menu/:lang', name: 'menu', component: Menu, meta: { requiresAuth: true } },
        { path: '/item/id/:id/:lang/:report_type?', name: 'item', component: Item, meta: { requiresAuth: true }},
        { path: '/user/id/:id/:lang', name: 'user', component: User, meta: { requiresAuth: true }},
        { path: '/user_list/:lang', name: 'user_list', component: UserList, meta: { requiresAuth: true }},
        { path: '/wish_list/:lang', name: 'wish_list', component: WishList, meta: { requiresAuth: true }},
        { path: '/account_activation/:lang/user/id/:id?', name: 'account_activation', component: AccountActivation, meta: { requiresAuth: true }},
        { path: '/for_supporters/:lang', name: 'for_supporters', component: InfoForSupporters },
        {
            path: '/items_management/:lang/:filter?',
            name: 'items_management',
            component: ItemsManagement,
            meta: { requiresAuth: true }
        }
    ]
});

router.beforeResolve((to, from, next) => {
    storeUtil.setLang(to.params.lang);
    storeUtil.setLoadingStateDefault();
    storeUtil.setEditMode(false);
    next();
});

router.afterEach((to, from) => {
});

export default router;

