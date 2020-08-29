<template>
    <div class="default-margin">
        <transition name="slide-fade">
            <table id="additional-menu" class="half-wide" v-if="!isLoading()">
                <tbody>
                <tr>
                    <td>
                        <button type="button"
                                v-on:click="openUsersList()">
                            {{translate("Users")}}
                        </button>
                    </td>
                </tr>
                <tr v-if="lang.toString() !== 'en'">
                    <td>
                        <a :href="getDictionaryUrl()"
                           target="_blank"
                           class="button">
                            {{"EN - " + lang.toUpperCase() + " " + translate("dictionary")}}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="https://docs.google.com/document/d/1XwULMlxG5JM5VYU-3qTmXYguF_kPvKFb2zE2iIFi_o0"
                           target="_blank"
                           class="button">
                            {{"Languages"}}
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>

            <table class="half-wide" v-if="isAdmin()">
                <tbody>
                <tr><td><hr></td></tr>
                <tr>
                    <td>{{"Admin section"}}</td>
                </tr>
                <tr>
                    <td>
                        <a href="https://docs.google.com/document/d/1g8YeaINmlH26XS1rqJ0oJRh0BN8mN8MIVRBh2MG4GQE"
                           target="_blank"
                           class="simple-link">
                            {{"Comments"}}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="https://docs.google.com/document/d/1JM_dDZIKjCRvrkOLRvvNwGtP3Al-Rakgtu-w4dFgB-c"
                           target="_blank"
                           class="simple-link">
                            {{"Info categories"}}
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </transition>
    </div>
</template>

<script>
import axios from "axios";
import {mapState} from "vuex";
import routerUtil from "../../util/routerUtil";
import DefaultButton from "../element/button/DefaultButton";
import axiosUtil from "../../util/axiosUtil";
import userUtil from "../../util/userUtil";
import basicComponent from "@/mixin/basicComponent";
import storeUtil from "@/util/storeUtil";

export default {
        name: "Menu",
        components: {DefaultButton},
        mixins: [basicComponent],
        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                dictionaryId: state => state.dictionary.dictionaryId
            })
        },

        mounted() {
            storeUtil.setLoadingStateOff();
        },

        data() {
            return {
                uploadMessage: "",
                localizedUploadMessage: ""
            }
        },

        methods: {
            isGuest() {
                return userUtil.isGuest();
            },

            isAdmin() {
                return userUtil.isAdmin(this.itemView);
            },

            openUsersList() {
                routerUtil.toUserList(this.$route.params.lang);
            },

            getDictionaryUrl() {
                let googleDocUrl = "https://docs.google.com/document/d/";
                return googleDocUrl + this.dictionaryId
            },

            uploadLangs(event) {
                this.upload(event, "langs");
            },

            uploadInfoCategories(event) {
                this.upload(event, "info_categories");
            },

            upload(event, fileName) {
                let lang = this.getLang();
                if (!routerUtil.validLang(lang)) {
                    let message = "Invalid language: " + lang;
                    console.log(message);
                    this.uploadMessage = message;
                    return;
                }
                console.log("Upload started");
                let input = event.target;
                let file = input.files[0];
                if (file !== null) {
                    let reader = new FileReader();
                    reader.readAsText(file);
                    reader.onload = (e) => {
                        let message = {
                            text: e.target.result
                        };
                        axios
                            .put(this.basicUrl
                                + "/" + "file"
                                + "/" + fileName
                                + "/" + "upload"
                                + "/" + userUtil.getUserName()
                                + "/" + lang, message)
                            .then(response => {
                                this.uploadMessage = response.data.text;
                                this.localizedUploadMessage = response.data.localizedText;
                                this.reset();
                            })
                            .catch(error => {
                                this.uploadMessage = response.data.text;
                                this.localizedUploadMessage = response.data.localizedText;
                            });
                    };
                }
            },

            refresh() {
                let message = this.uploadMessage;
                console.log(message);
                if (!this.isEmpty(message) && message.includes("New language added: ")) {
                    let newLang = message.split(": ")[1];
                    routerUtil.setLang(newLang, this.$route);
                }
                axiosUtil.setLangsAndDictionary(this.$route.params.lang);
                this.uploadMessage = "";
                this.localizedUploadMessage = "";
            },

            reset() {
                const input = this.$refs.fileInput;
                input.type = 'text/plain';
                input.type = 'file';
            }
        }
    }
</script>

<style scoped>
    input[type="file"] {
        display: none;
    }
</style>