<template id="app">
    <div id="background">
        <div id="screen" style>
            <div id="app_bar" style="background-color: #617D89; height: 70px; padding: 10px">
                <table style="text-align: center; width: 100%; height: 100%">
                    <tbody>
                    <tr>
                        <td style="width: 80px">
                            <button
                                    v-show="false"
                                    @click="refresh()"
                                    id="back"
                                    style="width: 100%; height: 100%; background: none; font-size: larger; color: #252525">
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
                                    style="width: 100%; height: 100%; background: none; font-size: larger; color: #252525">
                                <b>Logout</b>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import Login from './components/Login.vue';
    import {mapState} from 'vuex';

    export default {
        name: 'app',

        components: {
            Login
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization
            })
        },

        methods: {
            logout() {
                this.removeToken();
                this.reload();
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
        margin-top: 60px;
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

    button {
        border-radius: 4px;
        border: none;
        background: #929292;
    }

    input {
        border-radius: 4px;
        border: none;
        background: navajowhite;
    }

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

</style>
