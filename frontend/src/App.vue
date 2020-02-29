<template id="app">
    <div id="main-div">
        <div id="screen">
            <AppBar/>
            <div style="text-align: left">
<!--                {{"appLanguage: " + appLanguage}}<br>-->
<!--                {{"$i18n.locale: " + $i18n.locale}}<br>-->
<!--                {{"errorMessage: " + errorMessage}}-->
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
            <div>
                <LangMenu/>
                <NavigationBar/>
                <CopyUrlButton/>
<!--                <MessagesSection/>-->
                <AdminMessage/>
                <UserMenu/>
                <router-view/>
            </div>
            <div id="place-of-creation">
                <div v-if="isHome()">
                    <p>{{"© 2017-2020 " + translate("Old Vehicles: Seals & Bearings")}}</p>
                </div>
            </div>
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
    import MessagesSection from "./components/special/MessagesSection";
    import routerUtil from "./util/routerUtil";
    import dictionaryUtil from "./util/dictionaryUtil";
    import CopyUrlButton from "./components/element/button/CopyUrlButton";
    import AdminMessage from "./components/special/AdminMessage";

    export default {
        name: 'app',

        components: {
            AdminMessage,
            CopyUrlButton,
            MessagesSection,
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
                userStatus: state => state.dictionary.userStatus,
                appLanguage: state => state.dictionary.appLanguage,
                langs: state => state.dictionary.langs,
                dictionary: state => state.dictionary.dictionary,
                errorMessage: state => state.dictionary.errorMessage,
                loginMessage: state => state.dictionary.loginMessage
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
            },

            isHome() {
                return routerUtil.isHome(this.$route);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
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
        display: none;
    }

    label, #screen {
        color: grey;
    }

    @media only screen and (max-width: 1640px) {
        #screen {
            border-radius: initial;
            width: 40%;
            height: 100%;
        }
    }

    @media only screen and (max-width: 1280px) {
        #screen {
            border-radius: initial;
            width: 100%;
            height: 100%;
        }
    }

    @media only screen and (max-width: 640px) {
        #yandex-donate-form {
            width: 100%;
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

    ::placeholder {
        color: #252525;
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

    .list-img {
        height: 52px;
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

    .simple-link {
        color: grey;
        text-decoration: underline;
    }

    table {
        /*border-collapse: collapse;*/
        /*border-style: hidden;*/
        border-collapse: initial;
        border-spacing: 10px;
    }

    table td {
        /*border: 10px solid transparent;*/
    }

    .default-margin {
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

    select {
        text-align-last: center;
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

    #place-of-creation {
        text-align: center;
        margin-top: 60px;
        margin-bottom: 20px;
    }

    .not-symmetrical-left {
        width: 80%;
    }

    .not-symmetrical-right {
        width: 20%;
    }

    .half-wide, .two-columns-table-left-column {
        width: 50%;
    }

    .two-column-table-right-column, .three-column-table-right-column, #get-all-table {
        width: 100%;
    }

    .third-part-wide, .three-column-table-left-column, .three-column-table-middle-column {
        width: 33.33%;
    }

    .three-column-table-left-column, .three-column-table-left-column-text {
        text-align: left;
    }

    .three-column-table-right-column {
        text-align: center;
    }

    .three-column-table-button-column {
    }

    .round-delete-button {
        background: red;
    }

    #menu-summary {
        text-align: center;
        font-weight: bold;
        font-size: large;
    }

    #remove-img-button {
        width: initial;
        background: red
    }
</style>
