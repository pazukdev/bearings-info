import itemViewUtil from "./itemViewUtil";
import store from "../plugins/store";
import router from "../plugins/router";

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

    toItem(itemId, lang) {
        router.push({name: "item", params: {id: itemId, lang}});
    },

    toUser(id, lang) {
        router.push({name: "user", params: {id, lang}});
    },

    toHome() {
        router.push({name: "home"});
    },

    toUserList() {
        router.push({name: "user_list"})
    },

    toLogin() {
        router.push({name: "login"});
        let itemViewStub = {
            wishListIds: [],
            userData: {
                itemName: "",
                rating: ""
            }
        };
        itemViewUtil.dispatchView(store, itemViewStub);
    },

    toItemsManagement(router) {
        router.push({name: "items_management"});
    }

}