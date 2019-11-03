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
<!--                {{"basicUrl: " + basicUrl}}<br>-->
<!--                {{"userName: " + userName}}<br>-->
<!--                {{"authorization: " + authorization}}<br>-->
<!--                {{"Item ids: " + itemIds}}<br>-->
<!--                {{"Is loading: " + loadingState}}<br>-->
<!--                {{"itemView: " + itemView}}<br>-->
<!--                {{"itemId: " + lastItemId}}<br>-->
            </div>
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
                itemIds: state => state.dictionary.itemIds,
                itemView: state => state.dictionary.itemView,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                lastItemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        methods: {
            isGuest() {
                return this.isAuthorized() && this.userName === "guest";
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
                            this.getItemView(this.lastItemId);
                        }
                    })
                    .catch(error => {
                        console.log("login as " + username + " failed");
                    });
            },

            logout() {
                this.$store.dispatch("setDefaultState");
                this.$router.push('/');
                console.log("logout");
                this.loginAsGuest();
            },

            openLoginForm() {
                this.$store.dispatch("setDefaultState");
                this.$router.push('/login');
                console.log("login form opened");
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            isBackButtonDisplayed() {
                return this.itemIds.length > 1 && !this.loadingState;
            },

            back() {
                console.log("back button taped");
                this.$store.dispatch("removeLastItemId");
                this.getItemView(this.lastItemId);
            },

            getItemView(itemId) {
                this.$store.dispatch("setLoadingState", true);
                axios
                    .get(this.basicUrl + "/item/get-view/" + itemId + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let previousItemView = response.data;
                        this.dispatchView(previousItemView);
                        this.logEvent("item view displayed: item", previousItemView);
                    });
            },

            dispatchView(itemView) {
                this.$store.dispatch("setItemView", itemView);
                this.$store.dispatch("setLoadingState", false);
            },

            logEvent(event, itemView) {
                console.log(event + ": "
                    + "id=" + itemView.itemId
                    + "; name=" + itemView.header.name);
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
