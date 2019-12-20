export default {

    getId(route) {
        return route.params.id;
    },

    isLoginPage(route) {
        return route.name === "login";
    },

    isHomePage(route) {
        return route.name === "home";
    }

}