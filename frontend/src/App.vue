<template id="app">
    <div id="main-div">
        <div id="screen">
            <AppBar :back-button-displayed="isBackButtonDisplayed()"
                    :login-button-displayed="isLoginButtonDisplayed()"
                    :logout-button-displayed="isLogoutButtonDisplayed()"
                    :language-select-displayed="isLanguageSelectDisplayed()"
                    :language="appLanguage"
                    @logout="logout"
                    @select-language="selectLanguage"
                    @open-login-form="openLoginForm"/>
            <div style="text-align: left">
<!--                {{"store: " + appLanguage}}<br>-->
<!--                {{"i18n: " + $i18n.locale}}<br>-->
<!--                {{this.$route.params.item_id}}<br>-->
<!--                {{this.$route.params.lang}}<br>-->
<!--                {{"basicUrl: " + basicUrl}}<br>-->
<!--                {{"userName: " + userName}}<br>-->
<!--                {{"authorization: " + authorization}}<br>-->
                {{"loadingState: " + loadingState}}<br>
                {{"editMode: " + editMode}}<br>
<!--                {{"itemView: " + itemView}}<br>-->
            </div>
            <router-view style="padding: 20px"/>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import AppBar from "./components/AppBar";
    import Home from "./components/Home";
    import LoadingScreen from "./components/LoadingScreen";

    export default {
        name: 'app',

        components: {
            LoadingScreen,
            AppBar,
            Home
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                loadingState: state => state.dictionary.loadingState,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                motorcycleCatalogueId: state => state.dictionary.motorcycleCatalogueId,
                appLanguage: state => state.dictionary.appLanguage
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

            selectLanguage(language) {
                this.$store.dispatch("setAppLanguage", language);
                this.$router.replace({ path: this.$router.currentRoute.path.replace(/\/[^\/]*$/, "/" + language) });
            },

            isGuest() {
                return this.isAuthorized() && this.userName.toString() === "guest";
            },

            pushToHome() {
                this.$router.push({ name: "home" });
            },

            pushToLoginForm() {
                this.$router.push({ name: "login" });
            },

            loginAsGuest() {
                let username = "guest";
                let password = "guest";
                let credentialsUrl ="username=" + username + "&" + "password=" + password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
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
                console.log("logout");
                this.loginAsGuest();
            },

            openLoginForm() {
                this.pushToLoginForm();
                console.log("logout");
                console.log("login form opened");
            },

            isAuthorized() {
                return this.authorization.toString() !== "";
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

            isLanguageSelectDisplayed() {
                // return !this.isLoginPage();
                return true;
            },

            isLoginPage() {
                return window.location.href.includes("login");
            },

            isHomePage() {
                return this.$route.name === "home";
            }
        }
    }
</script>

<style>
    * {
        margin: auto;
    }

    #main-div {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
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

    #screen::-webkit-scrollbar {
        /*display: none;*/
    }

    @media only screen and (max-width: 1280px) {
        #screen {
            border-radius: initial;
            width: 100%;
            height: 100%;
        }
    }

    table, button, select, input, .round-button {
        text-align: center;
    }

    table, button, select, input, textarea {
        width: 100%;
    }

    table {
        height: 100%;
    }

    hr, button, select, input {
        background: #808080;
    }

    input, textarea, #app_bar {
        background: #617D89;
    }

    button, select, input, textarea {
        border-radius: 4px;
        border: none;
    }

    select, input {
        height: 52px;
    }

    button, label {
        min-height: 52px;
        max-height: 92px;
        color: #050505;
    }

    table {
        border-spacing: 6px;
        border-collapse: initial;
    }

    th {
        font-weight: normal;
    }

    img {
        max-width: 100%;
    }

    summary {
        text-align: left;
    }

    p, input, textarea {
        margin: auto;
    }

    textarea {
        /*resize: none;*/
    }

    .round-button, .round-delete-button {
        text-align: center;
        height: 32px;
        width: 32px;
        min-height: initial;
        max-height: initial;
        border-radius: 16px;
    }

    .bordered {
        border: 1px solid;
        border-radius: 6px;
        padding: 6px;
    }

    .no-border {
        border-spacing: 0;
    }

    .third-part-wide {
        width: 33.33%;
    }

    .alert-message {
        color: red;
    }

    .selected {
        background: #617D89;
    }

    .red-background {
        background: red;
    }

    .title {
        text-align: center;
    }
</style>
