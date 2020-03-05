<template>
    <div class="default-margin">
        <table id="additional-menu" class="half-wide">
            <tbody>
            <tr>
                <td>
                    <button type="button"
                            v-on:click="openUsersList()">
                        {{translate("Users")}}
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <a :href="getDictionaryDownloadUrl()" class="button" download>
                        {{translate("Download dictionary")}}
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="upload-button">
                        {{translate("Upload dictionary")}}
                        <input type="file" ref="fileInput" accept="text/plain" @change="uploadDictionary"/>
                    </label>
                </td>
            </tr>
            <tr v-if="!isEmpty(localizedUploadMessage)">
                <td>{{translate(localizedUploadMessage)}}</td>
            </tr>
            <tr v-if="isRefreshButtonVisible()">
                <td>{{translate("Refresh page to implement changes on current page")}}</td>
            </tr>
            <tr>
                <td v-if="isRefreshButtonVisible()">
                    <DefaultButton :text="translate('Refresh')" @on-click="refresh"/>
                </td>
            </tr>
            </tbody>
        </table>

        <table class="half-wide">
            <tbody>
            <tr>
                <td>{{"Admin section"}}</td>
            </tr>
            <tr v-if="isAdmin()">
                <td>
                    <a :href="getDownloadUrl('comments')" class="button" download>
                        {{"Download comments"}}
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="upload-button">
                        {{"Upload comments"}}
                        <input type="file" ref="fileInput" accept="text/plain" @change="uploadComments"/>
                    </label>
                </td>
            </tr>
            <tr v-if="isAdmin()">
                <td>
                    <a :href="getDownloadUrl('info_categories')" class="button" download>
                        {{"Download info categories"}}
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="upload-button">
                        {{"Upload info categories"}}
                        <input type="file" ref="fileInput" accept="text/plain" @change="uploadInfoCategories"/>
                    </label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";
    import DefaultButton from "../element/button/DefaultButton";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import axiosUtil from "../../util/axiosUtil";
    import userUtil from "../../util/userUtil";

    export default {
        name: "Menu",
        components: {DefaultButton},
        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                itemView: state => state.dictionary.itemView,
                langs: state => state.dictionary.langs
            })
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

            getDictionaryDownloadUrl() {
                // return this.basicUrl + "/file/dictionary_ru/download";
                let langParam = this.getLang();
                let lang = langParam === "en" ? "ru" : langParam;
                return this.basicUrl + "/file/dictionary_" + lang + "/download";
            },

            getDownloadUrl(fileName) {
                return this.basicUrl + "/file/" + fileName + "/download";
            },

            uploadDictionary(event) {
                this.upload(event, "dictionary");
            },

            uploadComments(event) {
                this.upload(event, "comments");
            },

            uploadInfoCategories(event) {
                this.upload(event, "info_categories");
            },

            upload(event, fileName) {
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
                                + "/" + this.getLang(), message)
                            .then(response => {
                                this.uploadMessage = response.data.text;
                                this.localizedUploadMessage = response.data.localizedText;
                                this.reset();
                            });
                    };
                }
            },

            refresh() {
                let message = this.uploadMessage;
                if (!this.isEmpty(message) && message.includes("New language added: ")) {
                    let newLang = message.split(": ")[1];
                    routerUtil.setLang(newLang, this.$route);
                }
                axiosUtil.setLangsAndDictionary(this.$route.params.lang);
                this.uploadMessage = "";
                this.localizedUploadMessage = "";
            },

            isRefreshButtonVisible() {
                return !this.isEmpty(this.uploadMessage) && !this.uploadMessage.toLowerCase().includes('not accepted');
            },

            reset() {
                const input = this.$refs.fileInput;
                input.type = 'text/plain';
                input.type = 'file';
            },

            isEmpty(message) {
                return shared.isEmpty(message);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            getLang() {
                return routerUtil.getLang(this.$route);
            }
        }
    }
</script>

<style scoped>
    input[type="file"] {
        display: none;
    }
</style>