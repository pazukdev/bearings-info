<template id="app">
    <div id="main-div">
                <div class="iphone-x">
                    <div class="camera-panel"></div>
                    <div class="speaker"/>
                    <div class="camera"/>
                    <div class="content">
                        <div id="app-bar-spacer"/>
                        <AppBar/>
                        <div style="text-align: left">
<!--                                            {{"$i18n.locale: " + $i18n.locale}}<br>-->
<!--                                            {{"errorMessage: " + errorMessage}}-->
<!--                                            {{"lang: " + lang}}<br>-->
<!--                                            {{"langs: " + langs}}<br>-->
<!--                                            {{"dictionaryId: " + dictionaryId}}<br>-->
<!--                                            {{dictionary.length}}<br>-->
<!--                                            {{this.$route.params.item_id}}<br>-->
<!--                                            {{this.$route.params.lang}}<br>-->
<!--                                            {{"basicUrl: " + basicUrl}}<br>-->
<!--                                            {{"userId: " + userData.id}}<br>-->
<!--                                            {{"userName: " + userData.name}}<br>-->
<!--                                            {{"authorization: " + authorization}}<br>-->
<!--                                            {{"loadingState: " + loadingState}}<br>-->
<!--                                            {{"editMode: " + editMode}}<br>-->
<!--                                            {{"itemView: " + itemView.userData.name}}<br>-->
<!--                                            {{"itemView: " + itemView.userData.id}}<br>-->
                        </div>
                        <div>
                            <LangMenu/>
                            <NavigationBar/>
                            <CopyUrlButton/>
                            <AdminMessage/>
                            <UserMenu/>
                            <AboutApp/>

                            <router-view/>
                        </div>
                        <div id="place-of-creation">
                            <div v-if="isHome() && !loadingState" class="default-margin">
                                <p style="height: 40px" class="bottom-border">
                                    {{"© 2017-2020 " + translate("Old Vehicles: Seals & Bearings")}}
                                </p>
                                <br>
                                <DonationSection/>
                                <br>
                                <br>
                            </div>
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
    import userUtil from "./util/userUtil";
    import DonationSection from "./components/DonationSection";
    import AboutApp from "./components/AboutApp";

    export default {
        name: 'app',

        components: {
            AboutApp,
            DonationSection,
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
                userStatus: state => state.dictionary.userStatus,
                lang: state => state.dictionary.lang,
                langs: state => state.dictionary.langs,
                dictionary: state => state.dictionary.dictionary,
                dictionaryId: state => state.dictionary.dictionaryId,
                errorMessage: state => state.dictionary.errorMessage,
                loginMessage: state => state.dictionary.loginMessage,
                userData: state => state.dictionary.userData
            })
        },

        created() {
            console.log("App: created()");
            this.setBasicUrl();
            if (!this.isAuthorized()) {
                this.loginAsGuest();
            }
            // this.setLangsAndDictionary();
        },

        methods: {
            setLangsAndDictionary() {
                console.log("App: setLangsAndDictionary()");
                if (this.langs.length < 1) {
                    let urlLang = this.$route.params.lang;
                    console.log("App: axiosUtil.setLangsAndDictionary(urlLang)");
                    axiosUtil.setLangsAndDictionary(urlLang);
                }
            },

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
                return itemViewUtil.isAuthorized(axiosUtil.getAuthorization());
            },

            isGuest() {
                return userUtil.isGuest();
            },

            loginAsGuest() {
                axiosUtil.loginAsGuest(false, this.$route.params.lang);
            },

            isHome() {
                return routerUtil.isHome(this.$route);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            getUserName() {
                return userUtil.getUserName();
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

    .iphone-x {
        position: relative;
        width: 410px;
        height: 840px;
        border-radius: 40px;
        box-shadow: 0 0 0 11px #101010, 0 0 0 13px #212121, 0 0 0 20px #101010;
    }

    .camera-panel:before, .camera-panel:after {
        content: '';
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
    }

    .camera-panel:before {
        top: 0;
        width: 64%;
        height: 30px;
        background-color: #101010;
        border-radius: 0 0 40px 40px;
    }

    .speaker, .camera {
        position: absolute;
        display: block;
        color: transparent;
    }

    .speaker {
        top: 0;
        left: 50%;
        transform: translate(-50%, 6px);
        height: 8px;
        width: 15%;
        background-color: #101010;
        border-radius: 8px;
        box-shadow: inset 0 -3px 3px 0 rgba(256, 256, 256, 0.2);
    }


    .camera {
        left: 80px;
        top: 0;
        transform: translate(180px, 4px);
        width: 12px;
        height: 12px;
        background-color: #101010;
        border-radius: 12px;
        box-shadow: inset 0 -3px 2px 0 rgba(256, 256, 256, 0.2);
    }

    .camera:after {
        content: '';
        position: absolute;
        background-color: #2d4d76;
        width: 6px;
        height: 6px;
        top: 2px;
        left: 2px;
        display: block;
        border-radius: 4px;
        box-shadow: inset 0 -2px 2px rgba(0, 0, 0, 0.5);
    }

    .content {
        width: 100%;
        height: 100%;
        border-radius: 40px;
        background: #212121;
        justify-content: center;
        align-items: center;
        overflow: auto;
        -ms-overflow-style: none;
        scrollbar-width: none;
    }

    .content::-webkit-scrollbar {
        display: none;
    }

    label, .content {
        color: grey;
    }

    #app-bar-spacer {
        background: #617D89;
        height: 20px;
    }

    #yandex-donate-form {
        width: 100%;
        height: 330px;
    }

    @media only screen and (max-width: 1640px) {
        .iphone-x {
            position: initial;
            width: 100%;
            height: 100%;
            border-radius: initial;
            box-shadow: initial;
        }

        .content {
            border-radius: initial;
            width: 40%;
            height: 100%;
        }

        .speaker, .camera, .camera-panel, #app-bar-spacer {
            display: none;
        }

        #yandex-donate-form {
            width: 440px;
        }
    }

    @media only screen and (max-width: 1280px) {
        .content {
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

    .bottom-border {
        border-bottom: 1px solid grey;
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
        max-height: 240px;
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