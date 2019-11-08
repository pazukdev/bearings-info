<template>
    <div id="app_bar">
        <table>
            <tbody>
            <tr>
                <td class="app-bar-side-column">
                    <button
                            v-show="backButtonDisplayed"
                            @click="back()"
                            id="back"
                            class="app-bar-button">
                        <b>Back</b>
                    </button>
                </td>
                <td class="app-bar-middle-side-column"></td>
                <td id="appName">
                    <b>Bearings info</b>
                </td>
                <td class="app-bar-middle-side-column">
                    <select v-model="$i18n.locale">
                        <option v-for="(lang, i) in languages" :key="`Lang${i}`" :value="lang">{{ lang }}</option>
                    </select>
                </td>
                <td class="app-bar-side-column">
                    <button
                            v-show="logoutButtonDisplayed"
                            @click="logout()"
                            id="logout"
                            class="app-bar-button">
                        <b>Logout</b>
                    </button>
                    <button
                            v-show="loginButtonDisplayed"
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
</template>

<script>
    export default {
        name: 'AppBar.vue',

        props: {
            backButtonDisplayed: Boolean,
            logoutButtonDisplayed: Boolean,
            loginButtonDisplayed: Boolean
        },

        data() {
            return {
                languages: ["en", "ru"]
            }
        },

        methods: {
            logout() {
                this.$emit("logout");
            },

            openLoginForm() {
                this.$emit("open-login-form");
            },

            back() {
                console.log("back button taped");
                window.history.back();
            }
        }
    }
</script>

<style scoped>
    select {
        width: initial;
        height: initial;
        background: initial;
        border-radius: initial;
    }

    .app-bar-side-column {
        width: 20%;
    }

    .app-bar-middle-side-column {
        width: 12%;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
        font-size: larger;
        color: #212121
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
        font-size: x-large;
    }
</style>