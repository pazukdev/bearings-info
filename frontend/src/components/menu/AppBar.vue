<template>
    <div id="app_bar">
        <table class="dark">
            <tbody>
            <tr>
                <td class="app-bar-side-column">
                    <button v-show="isBackButtonDisplayed()"
                            @click="back()"
                            id="back"
                            class="dark background-blue app-bar-button">
                        {{translate("Back")}}
                    </button>
                </td>
                <td id="appName">
                    {{translate("Old Vehicles")}}
                </td>
                <td class="app-bar-side-column">
                    <button v-show="isLogoutButtonDisplayed()"
                            @click="logout()"
                            id="logout"
                            class="dark background-blue app-bar-button">
                        {{translate("Logout")}}
                    </button>
                    <button v-show="isLoginButtonDisplayed()"
                            @click="openLoginForm()"
                            id="login"
                            class="dark background-blue app-bar-button">
                        {{translate("Login")}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import routerUtil from "../../util/routerUtil";
    import axiosUtil from "../../util/axiosUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: 'AppBar',

        mixins: [basicComponent, view],

        methods: {
            logout() {
                axiosUtil.logout(this.$route.params.lang);
            },

            openLoginForm() {
                routerUtil.toLogin();
                console.log("logout");
                console.log("login form opened");
            },

            back() {
                console.log("back button taped");
                routerUtil.back();
            },

            isBackButtonDisplayed() {
                if (this.editMode) {
                    return false;
                }
                return this.$route.name !== "home" && this.$route.name !== "login";
                // return !(this.isLoginPage() || this.isHomePage() || this.loadingState);
            },

            isLogoutButtonDisplayed() {
                return !this.isGuest() && this.isAuthorized();
            },

            isLoginButtonDisplayed() {
                return this.isGuest() && !this.isLoginPage();
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
            },

            isHomePage() {
                return routerUtil.isHome(this.$route);
            }

        }
    }
</script>

<style scoped>
    table, .app-bar-button {
        font-weight: bold;
        text-align: center;
    }

    .app-bar-side-column {
        width: 20%;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        border-radius: 26px;
    }

    .app-bar-button:hover {
        background: #404040;
        color: black;
    }

    #app_bar {
        padding-top: 5px;
        min-height: 56px;
        max-height: 200px;
    }

    #appName, .app-bar-button {
        font-size: large;
    }
</style>