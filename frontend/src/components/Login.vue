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
                    <input type="text" name="username" v-model="credentials.username"/>
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td>
                    <input type="password" name="password" v-model="credentials.password"/>
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
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                isLogin: true,
                credentials: {
                    username: "",
                    password: ""
                }
            };
        },
        methods: {

            login() {
                let credentials = {
                    username: "user",
                    password: "password"
                };
                let headerData ="Basic " + btoa(credentials.username + ":" + credentials.password);
                axios
                    .get('/backend/login', {
                    headers: {
                        Authorization: headerData
                    }
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$store.dispatch("setAuthorizationHeaderData", headerData);
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