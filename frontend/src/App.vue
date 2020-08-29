<template id="app">
    <div id="main-div">
                <div class="iphone-x">
                    <div class="camera-panel"></div>
                    <div class="speaker"/>
                    <div class="camera"/>
                    <div class="content">
                        <div id="app-bar-spacer"/>
                        <AppBar/>
                        <div>
<!--                            <p v-if="!isEmpty(userData)">{{userData.name}}</p>-->
<!--                            <p v-if="!isEmpty(itemView)">{{itemView.cachedViews}}</p>-->
<!--                          <p v-if="!isEmpty(itemView)">{{itemView.lang}}</p>-->
                            <LangMenu/>
                            <NavigationBar/>
                            <CopyUrlButton v-if="false"/>
                            <AdminMessage/>
                            <UserMenu/>
                          <div v-if="isHome() || isMenu()">
                              <AboutApp/>
                              <Info/>
                              <NewsSection/>
                          </div>
                            <AppSettings v-if="isAdmin()"/>
                            <router-view/>
                        </div>
                        <AppGroupsSection v-if="isHome() && !loadingState" :beer-glass-rendered="true"/>
                        <div style="height: 200px"/>
                    </div>
                </div>
    </div>
</template>

<script>
import axios from "axios";
import {mapState} from 'vuex';
import view from "./mixin/view";
import basicComponent from "./mixin/basicComponent";
import routerUtil from "./util/routerUtil";
import axiosUtil from "./util/axiosUtil";

const AppBar = () => import("./components/menu/AppBar");
const Home = () => import("./components/router/Home");
const LoadingScreen = () => import("./components/special/LoadingScreen");
const NavigationBar = () => import("./components/menu/NavigationBar");
const UserMenu = () => import("./components/menu/UserMenu");
const LangMenu = () => import("./components/menu/LangMenu");
const MessagesSection = () => import("./components/special/MessagesSection");
const CopyUrlButton = () => import("./components/element/button/CopyUrlButton");
const AdminMessage = () => import("./components/special/AdminMessage");
const DonationSection = () => import("./components/DonationSection");
const AboutApp = () => import("./components/AboutApp");
const AppGroupsSection = () => import("./components/AppGroupsSection");
const NewsSection = () => import("./components/info/NewsSection");
const Info = () => import("./components/info/Info");
const SwitchElement = () => import("./components/element/SwitchElement");
const AppSettings = () => import("@/components/menu/AppSettings");

export default {
        name: 'app',

        components: {
            AppSettings,
            SwitchElement,
            Info,
            NewsSection,
            AppGroupsSection,
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

        metaInfo() {
            return {
                title: this.translate("Old Vehicles")
            }
        },

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userStatus: state => state.dictionary.userStatus,
                dictionary: state => state.dictionary.dictionary,
                dictionaryId: state => state.dictionary.dictionaryId,
                errorMessage: state => state.dictionary.errorMessage,
                loginMessage: state => state.dictionary.loginMessage,
                cachedViews: state => state.dictionary.cachedViews
            })
        },

        created() {
            console.log("App: created()");
            this.setBasicUrl();
            if (!this.isAuthorized()) {
                this.loginAsGuest();
            }
            this.getCountries();
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

            getCountries() {
                console.log("get countries list");
                axios
                    .get("https://restcountries.eu/rest/v2/all")
                    .then(response => {
                        this.$store.dispatch("setCountries", response.data)
                    })
            },

            loginAsGuest() {
                axiosUtil.loginAsGuest(false, this.$route.params.lang);
            },

            isHome() {
                return routerUtil.isHome(this.$route);
            },

            isMenu() {
                return routerUtil.isMenu(this.$route);
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

    .slide-fade-enter-active {
        transition: all .3s ease;
    }
    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-enter, .slide-fade-leave-to {
        transform: translateX(200px);
        opacity: 0;
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

    .button:active {
        background-color: #617D89;
        /*box-shadow: 0 5px gray;*/
        transform: translateY(4px);
    }

    button:active {
        background-color: #617D89;
        transform: translateY(4px);
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
        border: 2px solid #FF5722;
    }

    input:not([type="submit"]):not([type="button"]):valid {
        /*border: 1px solid green;*/
    }

    .round-button {
        height: initial;
        width: initial;
        border-radius: initial;
        border-radius: 20px;
        height: 40px;
        width: 40px;
        min-height: initial;
        max-height: initial;
        /*border: 2px solid grey;*/
        font-size: x-large;
        font-weight: bold;
        background: none;
    }

    .simple-link:hover {
        color: grey;
    }

    input:hover, button:hover, .button:hover, select:hover {
        opacity: 0.8;
        cursor: pointer;
    }

    .round-button:hover {
        opacity: initial;
        background: #151515;
    }

    .small-img {
        width: 200px;
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

    .bold {
        font-weight: bold;
    }

    .alert-message, .red {
        color: #FF5722;
    }

    .background-red {
        background: #FF5722;
    }

    .green {
        color: #6ab04c;
    }

    .background-green {
        background: #6ab04c;
    }

    .blue {
        color: #617D89;
    }

    .background-blue {
        background: #617D89;
    }

    .grey {
        color: grey;
    }

    .background-grey {
        background: grey;
    }

    .dark {
        color: #212121;
    }

    .background-dark {
        background: #212121;
    }

    /*.background-grey:hover, .background-green:hover, .background-red:hover, .background-blue:hover {*/
    /*    opacity: 0.8;*/
    /*}*/

    .title {
        text-align: center;
    }

    .extra-wide {
        width: 80%;
    }

    .not-symmetrical-left {
        width: 60%;
    }

    .not-symmetrical-right {
        width: 10%;
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

    #menu-summary {
        text-align: center;
        font-weight: bold;
        font-size: large;
    }

    #remove-img-button {
        width: initial;
        background: #FF5722;
    }

</style>