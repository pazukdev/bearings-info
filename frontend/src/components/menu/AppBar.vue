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
                        {{$t('back')}}
                    </button>
                </td>
                <td id="appName">
                    {{"Soviet boxers seals & bearings"}}
                </td>
                <td class="app-bar-side-column">
                    <button
                            v-show="logoutButtonDisplayed"
                            @click="logout()"
                            id="logout"
                            class="app-bar-button">
                        {{$t('logout')}}
                    </button>
                    <button
                            v-show="loginButtonDisplayed"
                            @click="openLoginForm()"
                            id="login"
                            class="app-bar-button">
                        {{$t('loginButton')}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: 'AppBar',

        props: {
            backButtonDisplayed: Boolean,
            logoutButtonDisplayed: Boolean,
            loginButtonDisplayed: Boolean,
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
    table, .app-bar-button {
        color: #212121;
        font-weight: bold;
        text-align: center;
    }

    .app-bar-side-column {
        width: 20%;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
    }

    #app_bar {
        padding-top: 5px;
        min-height: 56px;
        max-height: 200px;
    }

    #appName, .app-bar-button {
        font-size: large;
    }
</style>