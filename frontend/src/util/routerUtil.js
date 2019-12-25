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

    toLogin(router) {
        router.push({name: "login"});
    },

    toItemsManagement(router) {
        router.push({name: "items_management"});
    },

}