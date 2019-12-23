export default {

    getId(route) {
        return route.params.id;
    },

    isLoginPage(route) {
        return route.name === "login";
    },

    isHomePage(route) {
        return route.name === "home";
    },

    toItem(router, itemId, lang) {
        router.push({ name: "item", params: {id: itemId, lang} });
    }

}