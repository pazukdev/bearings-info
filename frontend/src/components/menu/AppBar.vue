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
                        {{$t('back')}}
                    </button>
                </td>
                <td id="appName">
                    {{"Soviet boxers seals & bearings"}}
                </td>
                <td class="app-bar-side-column">
                    <button
                            v-show="isLogoutButtonDisplayed()"
                            @click="logout()"
                            id="logout"
                            class="app-bar-button">
                        {{$t('logout')}}
                    </button>
                    <button
                            v-show="isLoginButtonDisplayed()"
                            @click="openLoginForm()"
                            id="login"
                            class="app-bar-button">
                        {{$t('loginButton')}}
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

    export default {
        name: 'AppBar',

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                loadingState: state => state.dictionary.loadingState,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            logout() {
                routerUtil.toLogin();
                console.log("logout");
                let toHome = false;
                axiosUtil.loginAsGuest(this.basicUrl, toHome);
            },

            openLoginForm() {
                routerUtil.toLogin();
                console.log("logout");
                console.log("login form opened");
            },

            back() {
                console.log("back button taped");
                window.history.back();
            },

            isAuthorized() {
                return itemViewUtil.isAuthorized(this.authorization);
            },

            isGuest() {
                return itemViewUtil.isGuest(null, this.userName);
            },

            isBackButtonDisplayed() {
                return !this.isLoginPage() && !this.isHomePage() && !this.loadingState;
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