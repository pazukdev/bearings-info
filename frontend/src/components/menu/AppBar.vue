<template>
    <div id="app_bar">
        <table>
            <tbody>
            <tr>
                <td class="app-bar-side-column">
                    <button
                            v-show="isBackButtonDisplayed()"
                            @click="back()"
                            id="back"
                            class="app-bar-button">
                        {{translate("Back")}}
                    </button>
                </td>
                <td id="appName">
                    {{"Old Vehicles"}}
                </td>
                <td class="app-bar-side-column">
                    <button
                            v-show="isLogoutButtonDisplayed()"
                            @click="logout()"
                            id="logout"
                            class="app-bar-button">
                        {{translate("Logout")}}
                    </button>
                    <button
                            v-show="isLoginButtonDisplayed()"
                            @click="openLoginForm()"
                            id="login"
                            class="app-bar-button">
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
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";
    import axiosUtil from "../../util/axiosUtil";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: 'AppBar',

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                loadingState: state => state.dictionary.loadingState,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            logout() {
                axiosUtil.logout(this.basicUrl);
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

            isAuthorized() {
                return itemViewUtil.isAuthorized(this.authorization);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
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
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
    table, .app-bar-button {
        color: #212121;
        font-weight: bold;
        text-align: center;
    }

    .app-bar-side-column {
        width: 20%;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
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