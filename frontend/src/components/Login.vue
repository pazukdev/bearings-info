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
                    Nick name
                </td>
                <td>
                    <input v-model="alias"/>
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td>
                    <input type="password" v-model="password"/>
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
    import VueCookies from 'vue-cookies';

    export default {
        data() {
            return {
                checked: false,
                alias: "",
                password: "",
                name: "",
                isLogin: true,
                dictionaryData: {}
            };
        },
        methods: {
            login() {
                let credentials = {
                    alias: this.alias,
                    password: this.password,
                    name: this.name
                };
                function addAuthorization(response) {
                    let authorization = response.headers.authorization;
                    VueCookies.config('7d');
                    VueCookies.set('authorization', authorization);
                    axios.get('/backend/dictionary', {
                        headers: {
                            Authorization: authorization
                        }
                    }).then(response => {
                        this.$store.commit('dictionary/init', response.data);
                    });
                }
                if (this.isLogin) {
                    axios.post('/backend/login', credentials)
                        .then(response => {
                            if (response.status === 200) {
                                addAuthorization(response);
                            }
                        })
                } else {
                    axios.post('/backend/public/user', credentials)
                        .then(response => {
                            if (response.status === 200) {
                                addAuthorization(response);
                            }
                        })
                }
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