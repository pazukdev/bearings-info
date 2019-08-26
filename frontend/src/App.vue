<template id="app">
    <div id="background">
        <div id="screen" style>
            <div id="app_bar" style="background-color: #617D89; height: 70px; padding: 10px">
                <table style="text-align: center; width: 100%; height: 100%">
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
                                    v-show="isAuthorized()"
                                    @click="logout()"
                                    id="logout"
                                    class="app-bar-button">
                                <b>Logout</b>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            {{homeComponent}}
            <router-view style="padding: 20px"></router-view>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex';

    export default {
        name: 'app',

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                homeComponent: state => state.dictionary.homeComponent,
                incorrectCredentials: state => state.dictionary.incorrectCredentials
            })
        },

        methods: {
            logout() {
                this.removeToken();
                this.reload();
                this.$store.dispatch("clearHistory");
            },

            removeToken() {
                let token = null;
                this.$store.dispatch("setAuthorization", token);
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            reload() {
                window.location.reload();
            },

            getCurrentRouteName() {
                return this.$router.currentRoute.name;
            },

            isBackButtonDisplayed() {
                return this.homeComponent.length > 1;
            },

            isBackAllowedUrl() {
                return this.getCurrentRouteName() !== "login"
                    && this.getCurrentRouteName() !== "home";
            },

            back() {
                this.$store.dispatch("removeLastComponent");
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #background {
        background-color: black;
        height: 1024px;
        padding-top: 50px;
    }

    #screen {
        background-color: aliceblue;
        margin: auto;
        width: 480px;
        height: 700px;
        overflow-y: auto;
        border-radius: 10px;
    }

    #screen::-webkit-scrollbar {
        display: none;
    }

    #appName {
        text-align: center;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
        font-size: larger;
        color: #252525
    }

    .centred-table {
        text-align: center;
    }

    .get-all-table {
        text-align: center;
    }

    .creation-form {
        text-align: left;
        border-spacing: 20px;
        border-collapse: separate;
    }

    .content {
        width: 100%;
    }

    .right {
        width: 50%;
    }

    .default-width-1 {
        width: 160px;
    }

    .default-width-2 {
        width: 100px;
    }

    .full-width {
        width: 100%;
    }

    button {
        border-radius: 4px;
        border: none;
        background: #929292;
        height: 36px;
    }

    select {
        border-radius: 4px;
        text-indent: 10px;
        height: 36px;
    }

    input {
        border-radius: 4px;
        border: none;
        background: navajowhite;
        height: 36px;
        text-align: center;
    }

    table {
        width: 100%;
        text-align: left;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

</style>
