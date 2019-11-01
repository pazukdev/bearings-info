<template>
    <div id="login">
        <table>
            <tbody>
            <tr>
                <td id="login-or-signup-txt">
                    Please, {{buttonName().toLowerCase()}} or
                </td>
                <td>
                    <button v-on:click="switchForm()">{{buttonReverseName()}}</button>
                </td>
            </tr>
            <tr>
                <td>
                    Login
                </td>
                <td class="half-width">
                    <input type="text" name="username" v-model="username"/>
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td>
                    <input type="password" name="password" v-model="password"/>
                </td>
            </tr>
            <tr v-if="!isLogin">
                <td>
                    Repeat password
                </td>
                <td>
                    <input v-model="repeatedPassword"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button v-on:click="performLoginPageAction">{{buttonName()}}</button>
                </td>
            </tr>
            <tr v-if="incorrectCredentials" class="warning-message">
                <td colspan="2">
                    Incorrect login or password !
                </td>
            </tr>
            <tr v-for="message in validationMessages" v-bind:value="message" class="warning-message">
                <td colspan="2">
                    {{message}}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {
        data() {
            return {
                isLogin: true,
                username: "admin",
                password: "admin",
                repeatedPassword: "",
                validationMessages: []
            };
        },

        computed: {
            ...mapState({
                incorrectCredentials: state => state.dictionary.incorrectCredentials
            })
        },

        methods: {
            performLoginPageAction() {
                if (this.isLogin) {
                    this.login();
                } else {
                    this.signUp();
                }
            },

            loginIfValid(validationMessage) {
                this.validationMessages = validationMessage;
                if (this.validationMessages.length === 0) {
                    this.login();
                }
            },

            login() {
                this.setIncorrectCredentials(true);
                let credentialsUrl ="username=" + this.username + "&" + "password=" + this.password;
                axios
                    .post('/backend/login', credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.$store.dispatch("setLoadingState", true);
                            this.setIncorrectCredentials(false);
                            let authorization = response.data.Authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$store.dispatch("setUserName", this.username);
                            let specialMotorcycleCatalogueItemId = -2;
                            this.$store.dispatch("addItemId", specialMotorcycleCatalogueItemId);
                            this.$router.push({ path: '/'});
                        }
                    });
            },

            signUp() {
                let newUser = {
                    name: this.username,
                    password: this.password,
                    repeatedPassword: this.repeatedPassword
                };
                axios
                    .post("/backend/user/create", newUser)
                    .then(response =>  this.loginIfValid(response.data));
            },

            switchForm() {
                this.isLogin = !this.isLogin;
                this.resetData();
            },

            buttonName() {
                return this.isLogin ? "Log in" : "Sign up";
            },

            buttonReverseName() {
                return this.isLogin ? "Sign up" : "Log in";
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
            }
        }
    }
</script>

<style scoped>

    table {
        padding-top: 50%;
        text-align: left;
    }

    button {
        width: 50%;
    }

    .warning-message {
        text-align: center;
        color: red;
    }

    #login-or-signup-txt {
        text-align: right;
    }
</style>