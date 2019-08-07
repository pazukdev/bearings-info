<template>
    <div class="login">
        <table>
            <tbody>
            <tr>
                <td colspan="2">
                    <div>Please, {{buttonName().toLowerCase()}} or
                        <button v-on:click="switchForm">{{buttonReverseName()}}</button>
                    </div>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    Login
                </td>
                <td>
                    <input type="text" name="username" v-model="username"/>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    Password
                </td>
                <td>
                    <input type="password" name="password" v-model="password"/>
                </td>
            </tr>
            <tr style="text-align: left">
                <td>
                    <div v-if="!isLogin">Repeat password</div>
                </td>
                <td>
                    <input v-if="!isLogin" v-model="repeatedPassword"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button v-on:click="performLoginPageAction">{{buttonName()}}</button>
                </td>
            </tr>
            <tr v-if="incorrectCredentials" style="color: red">
                <td colspan="2">
                    Incorrect login or password !
                </td>
            </tr>
            <tr v-for="message in validationMessages" style="color: red">
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
                username: "",
                password: "",
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
                            this.setIncorrectCredentials(false);
                            let authorization = response.headers.authorization;
                            this.$store.dispatch("setAuthorization", authorization);
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
                this.$store.dispatch("setIncorrectCredentials", incorrectCredentials = true);
            }
        }
    }
</script>

<style scoped>
    table {
        margin-top: 120px;
        border-spacing: 20px;
        border-collapse: separate;
    }
</style>