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
                        {{$t('continueAsGuest')}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>

        <form id="login-form" @submit="performLoginPageAction">
            <table>
                <tbody>
                <tr v-if="validationMessages.length" style="text-align: left">
                    <td>
                        <ul v-for="message in validationMessages">
                            <li class="alert-message">{{message}}</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{"E-mail"}}
                            <input type="email" name="username" v-model="username"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{$t('password')}}
                            <input type="password" name="password" v-model="password"/>
                        </label>
                    </td>
                </tr>
                <tr v-if="!isLogin">
                    <td>
                        <label>{{$t("repeatPassword")}}
                            <input type="password" v-model="repeatedPassword"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input id="submit-login-form" type="submit" :value="buttonName()">
                    </td>
                </tr>
                <tr v-if="incorrectCredentials" class="warning-message">
                    <td colspan="2">
                        {{getIncorrectLoginOrPasswordMessage()}}
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

    export default {
        data() {
            return {
                isLogin: true,
                username: "pazuk1985@gmail.com",
                password: "admin",
                repeatedPassword: "",
                validationMessages: []
            };
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            validate() {

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
                this.username = "guest";
                this.password = "guest";
                this.login();
            },

            login() {
                let credentialsUrl ="username=" + this.username + "&" + "password=" + this.password;
                axios
                    .post(this.basicUrl + "/login", credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.setIncorrectCredentials(false);
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", this.username);
                            this.pushToHome();
                            console.log("logged in as " + this.username);
                        }
                    })
                    .catch(error => {
                        this.setIncorrectCredentials(true);
                        console.log("login failed: " + this.getIncorrectLoginOrPasswordMessage());
                    });
            },

            pushToHome() {
                this.$router.push({ name: "home" });
            },

            signUp() {
                let newUser = {
                    name: this.username,
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
                this.username = "";
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

    #submit-login-form {
        background: grey;
    }

    #login-page, #login-form {
        text-align: center;
    }

</style>