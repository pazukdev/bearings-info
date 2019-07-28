<template>
    <div class="login">
        <table>
            <tbody>
            <tr>
                <td colspan="2">
                    <div>Please, {{buttonName().toLowerCase()}} or
                        <button v-on:click="signUp">{{buttonReverseName()}}</button>
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
                    <div v-if="!isLogin">Name</div>
                </td>
                <td>
                    <input v-if="!isLogin" v-model="name"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button v-on:click="login">{{buttonName()}}</button>
                </td>
            </tr>
            <tr v-if="incorrectCredentials" style="color: red">
                <td colspan="2">
                    Incorrect login or password !
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
                password: ""
            };
        },

        computed: {
            ...mapState({
                incorrectCredentials: state => state.dictionary.incorrectCredentials
            })
        },

        methods: {

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
                this.isLogin = !this.isLogin;
            },
            buttonName() {
                return this.isLogin ? "Log in" : "Sign up";
            },
            buttonReverseName() {
                return this.isLogin ? "Sign up" : "Log in";
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