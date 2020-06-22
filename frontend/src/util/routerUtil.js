import itemViewUtil from "./itemViewUtil";
import router from "../plugins/router";
import storeUtil from "./storeUtil";
import shared from "./shared";
import store from "../plugins/store";

export default {

    refresh() {
        router.go(0);
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

    isMenu(route) {
        return route.name === "menu";
    },

    isItem(route) {
        return route.name === "item";
    },

    isItems(route) {
        return route.name === "items_management";
    },

    isUser(route) {
        return route.name === "user";
    },

    isWishlist(route) {
        return route.name === "wish_list";
    },

    toItem(id, lang) {
        router.push({name: "item", params: {id: id, lang: lang}});
    },

    toUser(id, lang) {
        router.push({name: "user", params: {id: id, lang: lang}});
    },

    toHome(lang, message) {
        if (shared.isEmpty(message)) {
            message = null;
        }
        router.push({name: "home", params: {lang: lang, message: message}});
    },

    toAccountActivation(lang, userId) {
        router.push({name: "account_activation", params: {lang: lang, id: userId}});
    },

    toUserList(lang) {
        router.push({name: "user_list", params: {lang: lang}})
    },

    toLogin(lang) {
        console.log("toLogin(lang)");
        router.push({name: "login", lang: lang});

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

        storeUtil.setAuthorization("");
        itemViewUtil.dispatchView(itemViewStub, lang);
    },

    toItemsManagement(filter, lang) {
        if (shared.isEmpty(filter)) {
            router.push({name: "items_management", params: {lang: lang}});
        } else {
            router.push({name: "items_management", params: {lang: lang, filter: filter}});
        }
    },

    toItemsSearch(filter, lang) {
        this.toItemsManagement(filter, lang);
    },

    selectLanguage(newLang, route) {
        this.setLang(newLang, route);
    },

    setLang(newLang, route) {
        console.log("setLang(): " + newLang);
        router.push({name: route.name, params: {lang: newLang}});
    },

    getLang(route) {
        if (shared.isEmpty(route)) {
            return store.getters.lang;
        }
        return route.params.lang;
    },

    validLang(lang) {
        return shared.isInArray(lang, store.getters.allowedLangs);
    }

}