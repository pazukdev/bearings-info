<template>
    <div id="login-page">
        <LoadingScreen v-if="isLoading()"/>
        <transition name="slide-fade">
            <div v-if="!isLoading()">
                <table class="equal-columns-table">
                    <tbody>
                    <tr>
                        <td>
                            <button id="switch-login-form-button" @click="switchForm()">
                                {{translate(buttonReverseName())}}
                            </button>
                        </td>
                        <td>
                            <button id="continue-as-guest-button" @click="loginAsGuest()">
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
                                    <input id="nickname-input" type="text" name="username"
                                           v-model="name" required
                                           pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                           :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                                </label>
                            </td>
                        </tr>
                        <tr v-if="!isLogin">
                            <td>
                                <label>{{"E-mail"}}
                                    <input id="email-input" type="email" name="username"
                                           v-model="email" required/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>{{translate("Password")}}
                                    <input id="password-input" type="password" name="password"
                                           v-model="password" required
                                           pattern="[a-zA-Z0-9_ \\-]{2,26}"
                                           :title="translate('Length: 2 - 26 characters: english letters, numbers, - , _ , space')"/>
                                </label>
                            </td>
                        </tr>
                        <tr v-if="!isLogin">
                            <td>
                                <label>{{translate("Repeat password")}}
                                    <input id="repeat-password-input" type="password"
                                           v-model="repeatedPassword" required
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
        </transition>
    </div>
</template>

<script>
import axios from 'axios';
import {mapState} from 'vuex';
import AlertMessagesSection from "../info/AlertMessagesSection";
import axiosUtil from "../../util/axiosUtil";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";
import shared from "../../util/shared";
import storeUtil from "../../util/storeUtil";
import LoadingScreen from "../special/LoadingScreen";
import routerUtil from "../../util/routerUtil";

export default {
        components: {LoadingScreen, AlertMessagesSection},
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

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                storeUtil.setLoadingStateOff();
            },

            performLoginPageAction: function (e) {
                e.preventDefault();
                if(!this.isEmpty(this.itemView.userData)
                    && !this.isEmpty(this.itemView.userData.message)) {
                    this.itemView.userData.message = null;
                }
                if (this.isLogin) {
                    let user = {
                        name: this.name,
                        password: this.password
                    }
                    axiosUtil.login(user, true, this.$route.params.lang);
                } else {
                    this.signUp();
                }
            },

            loginAsGuest() {
                axiosUtil.loginAsGuest(true, this.$route.params.lang);
            },

            signUp() {
                storeUtil.setLoadingStateCreating();

                let newUser = {
                    name: this.name,
                    email: this.email,
                    password: this.password,
                    repeatedPassword: this.repeatedPassword,
                    status: "pending",
                    role: "USER",
                    activationUrl: shared.getCurrentLocation().origin + "/#/account_activation/" + this.$route.params.lang + "/user/id/"
                };
                axios
                    .post(axiosUtil.getBasicUrl() + "/user/create/" + this.$route.params.lang, newUser)
                    .then(response => {
                        newUser.repeatedPassword = "-";

                        this.validationMessages = response.data;
                        if (this.validationMessages.length === 0) {
                            console.log("a new user created: " + newUser.name);
                            routerUtil.toAccountActivation(this.getLang(), null);
                            // axiosUtil.login(newUser, true, this.$route.params.lang);
                        }
                        storeUtil.setLoadingStateOff();

                        // this.loginIfValid(response.data, newUser);
                    })
                    .catch(error => {
                        console.log(error);
                        storeUtil.setLoadingStateOff();
                    });
            },

            // loginIfValid(validationMessages, newUser) {
            //     this.validationMessages = validationMessages;
            //     if (this.validationMessages.length === 0) {
            //         console.log("a new user created: " + newUser.name);
            //         axiosUtil.login(newUser, true, this.$route.params.lang);
            //     }
            //     storeUtil.setLoadingStateOff();
            // },

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