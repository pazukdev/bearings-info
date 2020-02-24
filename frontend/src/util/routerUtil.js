import itemViewUtil from "./itemViewUtil";
import router from "../plugins/router";
import storeUtil from "./storeUtil";
import shared from "./shared";

export default {

    refresh() {
        router.go();
    },

    back() {
        router.go(-1);
    },

    getId(route) {
        return route.params.id;
    },

    isLogin(route) {
        return route.name === "login";
    },

    isHome(route) {
        return route.name === "home";
    },

    toItem(id, lang) {
        router.push({name: "item", params: {id, lang}});
    },

    toUser(id, lang) {
        router.push({name: "user", params: {id, lang}});
    },

    toMenu(lang) {
        router.push({name: "menu", params: {lang}});
    },

    toHome() {
        router.push({name: "home"});
    },

    toUserList() {
        router.push({name: "user_list"})
    },

    toWishlist() {
        router.push({name: "wish_list"});
    },

    toLogin() {
        router.push({name: "login"});
        let message = {
            text: "Your account has been blocked by the administrator",
            contact: "Contact us by email: pazukdev@gmail.com"
        };

        let itemViewStub = {
            wishListIds: [],
            userData: {
                itemName: "",
                rating: "",
                message: storeUtil.userIsBlocked() ? message : null
            }
        };
        itemViewUtil.dispatchView(itemViewStub);
    },

    toItemsManagement(router) {
        router.push({name: "items_management"});
    },

    toItemsSearch(filter) {
        if (shared.isEmpty(filter)) {
            this.toItemsManagement(router);
        } else {
            router.push({name: "items_management", params: {filter}});
        }
    },

    setLang(lang, route) {
        router.push({name: route.name, params: {lang}});
    }

}