<template id="app">
    <div id="main-div">
        <div id="screen">
            <AppBar/>
            <LangMenu/>
            <NavigationBar/>
            <UserMenu/>
            <div style="text-align: left">
<!--                {{"store: " + appLanguage}}<br>-->
<!--                {{"i18n: " + $i18n.locale}}<br>-->
<!--                {{"langs: " + langs}}-->
<!--                {{this.$route.params.item_id}}<br>-->
<!--                {{this.$route.params.lang}}<br>-->
<!--                {{"basicUrl: " + basicUrl}}<br>-->
<!--                {{"userName: " + userName}}<br>-->
<!--                {{"authorization: " + authorization}}<br>-->
<!--                {{"loadingState: " + loadingState}}<br>-->
<!--                {{"editMode: " + editMode}}<br>-->
<!--                {{"itemView: " + itemView}}<br>-->
            </div>
            <router-view/>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    import AppBar from "./components/menu/AppBar";
    import Home from "./components/router/Home";
    import LoadingScreen from "./components/special/LoadingScreen";
    import NavigationBar from "./components/menu/NavigationBar";
    import UserMenu from "./components/menu/UserMenu";
    import LangMenu from "./components/menu/LangMenu";
    import itemViewUtil from "./util/itemViewUtil";
    import axiosUtil from "./util/axiosUtil";

    export default {
        name: 'app',

        components: {
            LangMenu,
            UserMenu,
            NavigationBar,
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
                appLanguage: state => state.dictionary.appLanguage,
                langs: state => state.dictionary.langs
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

            isAuthorized() {
                return itemViewUtil.isAuthorized(this.authorization);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

            loginAsGuest() {
                let toHome = true;
                axiosUtil.loginAsGuest(this.basicUrl, toHome);
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
        width: 480px;
        height: 800px;
        border-radius: 10px;
    }

    #screen::-webkit-scrollbar {
        /*display: none;*/
    }

    label, #screen {
        color: grey;
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

    table, button, select, input, textarea, label, a.button {
        width: 100%;
    }

    table {
        height: 100%;
    }

    hr, button, select, input, a.button, label.upload-button, #submit-login-form {
        background: grey;
        color: #050505;
    }

    input, textarea, #app_bar {
        background: #617D89;
    }

    button, select, input, textarea, img, label, a.button {
        border-radius: 4px;
        border: none;
    }

    select, input {
        height: 52px;
    }

    button, a.button, label.upload-button {
        min-height: 52px;
        max-height: 92px;
    }

    a.button, label.upload-button {
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
    }

    label.upload-button {
        cursor: pointer;
    }

    table {
        border-collapse: initial;
        border-spacing: 10px;
    }

    hr, .default-margin {
        margin: 10px;
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

    input:not([type="submit"]):not([type="button"]):invalid {
        border: 2px solid red;
    }

    input:not([type="submit"]):not([type="button"]):valid {
        /*border: 1px solid green;*/
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

    .equal-columns-table {
        table-layout: fixed;
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

    .background-grey {
        background: grey;
    }

    .background-darkgreen {
        background: darkgreen;
    }

    .red-background {
        background: red;
    }

    .title {
        text-align: center;
    }
</style>
