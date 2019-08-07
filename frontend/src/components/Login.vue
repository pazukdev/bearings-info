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
            <tr>
                <td>
                    Login
                </td>
                <td>
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
            <tr>
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
            <tr v-if="!messageIsEmpty()" style="color: red">
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
                message: ""
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

            login() {
                this.$store.dispatch("setIncorrectCredentials", true);
                let credentialsUrl ="username=" + this.username + "&" + "password=" + this.password;
                axios
                    .post('/backend/login', credentialsUrl)
                    .then(response => {
                        if (response.status === 200) {
                            this.$store.dispatch("setIncorrectCredentials", false);
                            let authorization = response.headers.authorization;
                            this.$store.dispatch("setAuthorization", authorization);
                            this.$router.push({ path: '/'});
                        }
                    });
            },

            signUp() {
                if (this.repeatedPassword !== this.password) {
                    this.message = "Passwords are different !";
                } else if (this.username === "") {
                    this.message = "Login is empty !";
                } else if (this.password === "") {
                    this.message = "Password is empty !";
                } else {
                    let newUser = {
                        name: this.username,
                        password: this.password,
                        role: "USER"
                    };
                    axios.post("/backend/user/create", newUser);
                    this.message = "Account is created !";
                    this.resetUserData();
                }
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
                this.clearMessage();
                this.resetUserData();
            },

            resetUserData() {
                this.username = "";
                this.password = "";
                this.repeatedPassword = "";
            },

            clearMessage() {
                this.message = "";
            },

            messageIsEmpty() {
                return this.message === "";
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