<template>
    <div id="login-page">
        <table class="equal-columns-table">
            <tbody>
            <tr>
                <td>
                    <button @click="switchForm()">
                        {{buttonReverseName()}}
                    </button>
                </td>
                <td>
                    <button @click="loginAsGuest()">
                        {{translate("Continue as guest")}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>

        <form id="login-form" @submit="performLoginPageAction">
            <div v-if="itemView.userData.message !== null" class="alert-message">
                {{itemView.userData.message.text}}<br>
                {{itemView.userData.message.contact}}
            </div>
            <table>
                <tbody>
                <tr>
                    <td>
                        <label>{{"Nickname"}}
                            <input type="text" name="username" v-model="name" required
                                   pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                   :title="$t('nameAndPasswordInputLabel')"/>
                        </label>
                    </td>
                </tr>
                <tr v-if="!isLogin">
                    <td>
                        <label>{{"E-mail"}}
                            <input type="email" name="username" v-model="email" required/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{$t('password')}}
                            <input type="password" name="password" v-model="password" required
                                   pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                   :title="$t('nameAndPasswordInputLabel')"/>
                        </label>
                    </td>
                </tr>
                <tr v-if="!isLogin">
                    <td>
                        <label>{{$t("repeatPassword")}}
                            <input type="password" v-model="repeatedPassword" required
                                   pattern="[a-zA-Z0-9_ \\-]{4,14}"
                                   :title="$t('nameAndPasswordInputLabel')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input id="submit-login-form" type="submit" :value="buttonName()">
                    </td>
                </tr>
                <tr v-if="incorrectCredentials" class="warning-message">
                    <td class="alert-message">
                        {{getIncorrectLoginOrPasswordMessage()}}
                    </td>
                </tr>
                <tr>
                    <td>
                        <AlertMessagesSection :messages="validationMessages"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import AlertMessagesSection from "../AlertMessagesSection";
    import routerUtil from "../../util/routerUtil";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        components: {AlertMessagesSection},
        data() {
            return {
                isLogin: true,
                name: "admin",
                email: "",
                password: "admin",
                repeatedPassword: "",
                validationMessages: []
            };
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                appLanguage: state => state.dictionary.appLanguage,
                loginMessage: state => state.dictionary.loginMessage,
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            translate(text) {
                return dictionaryUtil.translate(text);
            },

            performLoginPageAction: function (e) {
                e.preventDefault();
                if (this.isLogin) {
                    this.login();
                } else {
                    this.signUp();
                }
            },

            loginIfValid(validationMessages, newUserName) {
                this.validationMessages = validationMessages;
                if (this.validationMessages.length === 0) {
                    console.log("a new user created: " + newUserName);
                    this.login();
                }
            },

            loginAsGuest() {
                this.name = "guest";
                this.password = "guest";
                this.login();
            },

            login() {
                let credentialsUrl ="username=" + this.name + "&" + "password=" + this.password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.setIncorrectCredentials(false);
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", this.name);
                            routerUtil.toHome();
                            console.log("logged in as " + this.name);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                        this.setIncorrectCredentials(true);
                        console.log("login failed: " + this.getIncorrectLoginOrPasswordMessage());
                    });
            },

            signUp() {
                let newUser = {
                    name: this.name,
                    email: this.email,
                    password: this.password,
                    repeatedPassword: this.repeatedPassword
                };
                axios
                    .post(this.basicUrl + "/user/create", newUser)
                    .then(response => {this.loginIfValid(response.data, newUser.name)});
            },

            switchForm() {
                this.isLogin = !this.isLogin;
                this.resetData();
            },

            buttonName() {
                return this.isLogin ? this.$t("loginButton") : this.$t("signUp");
            },

            buttonReverseName() {
                return this.isLogin ? this.$t("signUp") : this.$t("loginButton");
            },

            resetData() {
                this.resetUserData();
                this.validationMessages = [];
                this.$store.dispatch("setIncorrectCredentials", false);
            },

            resetUserData() {
                this.name = "";
                this.email = "";
                this.password = "";
                this.repeatedPassword = "";
            },

            setIncorrectCredentials(incorrectCredentials) {
                this.$store.dispatch("setIncorrectCredentials", incorrectCredentials);
            },

            getIncorrectLoginOrPasswordMessage() {
                return this.$t("incorrectLoginOrPassword");
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }

    form {
        margin-top: 50px;
        width: 80%;
    }

    #login-page, #login-form {
        text-align: center;
    }

</style>