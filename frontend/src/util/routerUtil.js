import itemViewUtil from "./itemViewUtil";
import store from "../plugins/store";
import router from "../plugins/router";

export default {

    getId(route) {
        return route.params.id;
    },

    isLogin(route) {
        return route.name === "login";
    },

    isHome(route) {
        return route.name === "home";
    },

    toItem(router, itemId, lang) {
        router.push({name: "item", params: {id: itemId, lang}});
    },

    toHome(router) {
        router.push({name: "home"});
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
    },

}