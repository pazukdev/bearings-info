<template id="app">
    <div id="main-div">
        <div id="screen">
            <div id="app_bar">
                <table>
                    <tbody>
                    <tr>
                        <td style="width: 80px">
                            <button
                                    v-show="isBackButtonDisplayed()"
                                    @click="back()"
                                    id="back"
                                    class="app-bar-button">
                                <b>Back</b>
                            </button>
                        </td>
                        <td id="appName" style="text-align: center; font-size: x-large">
                            <b>Bearings info</b>
                        </td>
                        <td style="width: 80px">
                            <button
                                    v-show="!isGuest() && isAuthorized()"
                                    @click="logout()"
                                    id="logout"
                                    class="app-bar-button">
                                <b>Logout</b>
                            </button>
                            <button
                                    v-show="isGuest()"
                                    @click="openLoginForm()"
                                    id="login"
                                    class="app-bar-button">
                                <b>Login</b>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div style="text-align: left">
<!--                {{this.$route.params.item_id}}<br>-->
<!--                {{"basicUrl: " + basicUrl}}<br>-->
<!--                {{"userName: " + userName}}<br>-->
<!--                {{"authorization: " + authorization}}<br>-->
<!--                {{"Is loading: " + loadingState}}<br>-->
<!--                {{"itemView: " + itemView}}<br>-->
            </div>
<!--            <router-link to="/">Home</router-link>-->
<!--            <router-link to="/199">199</router-link>-->
            <router-view style="padding: 20px"></router-view>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {
        name: 'app',

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                motorcycleCatalogueId: state => state.dictionary.motorcycleCatalogueId
            })
        },

        created() {
            this.setBasicUrl();
            if (!this.isAuthorized()) {
                this.loginAsGuest();
            }
        },

        methods: {
            setBasicUrl() {
                let hostname = window.location.hostname;
                let basicUrl;
                if (hostname === "localhost") {
                    basicUrl = "backend";
                } else {
                    basicUrl = "https://bearings-info.herokuapp.com";
                }
                this.$store.dispatch("setBasicUrl", basicUrl);
            },

            isGuest() {
                return this.isAuthorized() && this.userName === "guest";
            },

            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}` });
            },

            pushToHome() {
                this.pushTo(this.motorcycleCatalogueId);
            },

            pushToLoginForm() {
                this.$router.push({ path: '/login'});
            },

            loginAsGuest() {
                let username = "guest";
                let password = "guest";
                let credentialsUrl ="username=" + username + "&" + "password=" + password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.$store.dispatch("setLoadingState", true);
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", username);
                            console.log("logged in as " + username);
                            this.pushToHome();
                        }
                    })
                    .catch(error => {
                        console.log("login as " + username + " failed");
                    });
            },

            logout() {
                this.pushToLoginForm();
                // this.$store.dispatch("setDefaultState");
                console.log("logout");
                this.loginAsGuest();
            },

            openLoginForm() {
                this.pushToLoginForm();
                // this.$store.dispatch("setDefaultState");
                console.log("logout");
                console.log("login form opened");
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            isBackButtonDisplayed() {
                return !this.isLoginPage() && !this.isHomePage() && !this.loadingState;
            },

            isLoginPage() {
                return window.location.href.includes("login");
            },

            isHomePage() {
                return this.$route.params.item_id === this.motorcycleCatalogueId.toString()
                    || this.$route.params.item_id === "home";
            },

            back() {
                console.log("back button taped");
                window.history.back();
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    #main-div {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100vh;
        background: black;
    }

    #screen {
        overflow: auto;
        background-color: #212121;
        color: #808080;
        width: 480px;
        height: 800px;
        border-radius: 10px;

    }

    @media only screen and (max-width: 1280px) {
        #screen {
            border-radius: initial;
            width: 100%;
            height: 100%;
        }
    }

    #screen::-webkit-scrollbar {
        display: none;
    }

    #app_bar {
        padding-top: 5px;
        min-height: 56px;
        max-height: 200px;
        text-align: center;
    }

    #appName {
        text-align: center;
        color: #212121;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
        font-size: larger;
        color: #212121
    }

    .round-button, .round-delete-button {
        text-align: center;
        height: 32px;
        width: 32px;
        border-radius: 16px;
    }

    .no-border {
        border-spacing: 0;
    }

    .third-part-wide {
        width: 33.33%;
    }

    table, button, select, input, .round-button {
        text-align: center;
    }

    table, button, select, input {
        width: 100%;
    }

    table {
        height: 100%;
    }

    hr, button, select, input {
        background: #808080;
    }

    input, #app_bar {
        background: #617D89;
    }

    button, select, input {
        border-radius: 4px;
        border: none;
        height: 52px;
    }

    table {
        border-spacing: 6px;
        border-collapse: separate;
    }

    th {
        font-weight: normal;
    }

    img {
        max-width: 100%;
    }

    .alert-message {
        color: red;
    }
</style>
