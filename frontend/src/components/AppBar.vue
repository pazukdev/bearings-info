<template>
    <div id="app_bar">
<!--        <div style="color: black">-->
<!--            {{"prop: " + language}}<br>-->
<!--            {{"data: " + newLanguage}}<br>-->
<!--        </div>-->
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
                <td class="app-bar-middle-side-column">
                    {{"v 2.0"}}
                </td>
                <td id="appName">
                    {{"Soviet boxers seals & bearings"}}
                </td>
                <td class="app-bar-middle-side-column">
                    <select v-if="languageSelectDisplayed"
                            v-model="newLanguage"
                            @change="selectLanguage()">
                        <option v-for="lang in languages" :value="lang">
                            {{lang}}
                        </option>
                    </select>
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
    import {mapState} from 'vuex';

    export default {
        name: 'AppBar.vue',

        props: {
            backButtonDisplayed: Boolean,
            logoutButtonDisplayed: Boolean,
            loginButtonDisplayed: Boolean,
            languageSelectDisplayed: Boolean,
            language: String
        },

        computed: {
            ...mapState({
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        data() {
            return {
                languages: ["en", "ru"],
                newLanguage: this.language
            }
        },

        methods: {
            selectLanguage() {
                this.$emit("select-language", this.newLanguage);
            },

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
    table, select, .app-bar-button {
        color: #212121;
        font-weight: bold;
        text-align: center;
    }

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