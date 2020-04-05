<template>
    <div id="login-page">
        <table class="equal-columns-table">
            <tbody>
            <tr>
                <td>
                    <button @click="switchForm()">
                        {{translate(buttonReverseName())}}
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
            <div class="alert-message"
                 v-if="!isEmpty(itemView.userData) && !isEmpty(itemView.userData.message)">
                {{translate(itemView.userData.message.text)}}<br>
                {{translate(itemView.userData.message.contact)}}
            </div>
            <table>
                <tbody>
                <tr>
                    <td>
                        <label>{{"Nickname"}}
                            <input type="text" name="username" v-model="name" required
                                   pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                   :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
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
                        <label>{{translate("Password")}}
                            <input type="password" name="password" v-model="password" required
                                   pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                   :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                        </label>
                    </td>
                </tr>
                <tr v-if="!isLogin">
                    <td>
                        <label>{{translate("Repeat password")}}
                            <input type="password" v-model="repeatedPassword" required
                                   pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                   :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input id="submit-login-form" type="submit" :value="translate(buttonName())">
                    </td>
                </tr>
                <tr v-if="incorrectCredentials" class="warning-message">
                    <td class="alert-message">
                        {{translate("Incorrect login or password!")}}
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

        <router-link :to="{name: 'user_agreement'}" class="simple-link">
            {{translate("User agreement")}}
        </router-link>

        <details class="default-margin" style="text-align: center">
            <summary style="text-align: center">{{translate("Forgot password?")}}</summary>
            {{translate("Password change via email is temporarily not available")}}<br>
            {{translate("Contact us by email: pazukdev@gmail.com")}}
        </details>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import AlertMessagesSection from "../info/AlertMessagesSection";
    import axiosUtil from "../../util/axiosUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        components: {AlertMessagesSection},
        data() {
            return {
                isLogin: true,
                name: "",
                email: "",
                password: "",
                repeatedPassword: "",
                validationMessages: []
            };
        },

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                loginMessage: state => state.dictionary.loginMessage
            })
        },

        methods: {
            performLoginPageAction: function (e) {
                e.preventDefault();
                if (this.isLogin) {
                    axiosUtil.login(this.name, this.password, true, this.$route.params.lang);
                } else {
                    this.signUp();
                }
            },

            loginAsGuest() {
                axiosUtil.loginAsGuest(true, this.$route.params.lang);
            },

            loginIfValid(validationMessages, newUserName) {
                this.validationMessages = validationMessages;
                if (this.validationMessages.length === 0) {
                    console.log("a new user created: " + newUserName);
                    axiosUtil.login(this.name, this.password, true, this.$route.params.lang);
                }
            },

            signUp() {
                let newUser = {
                    name: this.name,
                    email: this.email,
                    password: this.password,
                    repeatedPassword: this.repeatedPassword
                };
                axios
                    .post(axiosUtil.getBasicUrl() + "/user/create", newUser)
                    .then(response => {this.loginIfValid(response.data, newUser.name)});
            },

            switchForm() {
                this.isLogin = !this.isLogin;
                this.resetData();
            },

            buttonName() {
                return this.isLogin ? "Login" : "Sign up";
            },

            buttonReverseName() {
                return this.isLogin ? "Sign up" : "Login";
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