<template>
    <div v-if="!isGuest()">
        <table id="additional-menu" class="half-wide">
            <tbody>
            <tr>
                <td>
                    <button type="button"
                            v-on:click="openUsersList()">
                        {{$t("users")}}
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <a :href="getDownloadUrl('dictionary')" class="button" download="dictionary.txt">
                        {{$t("downloadDictionary")}}
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="upload-button">
                        {{$t("uploadDictionary")}}
                        <input type="file" ref="fileInput" accept="text/plain" @change="uploadDictionary"/>
                    </label>
                </td>
            </tr>
            <tr v-if="!isEmpty(uploadMessage)">
                <td>{{uploadMessage}}</td>
            </tr>
            <tr v-if="isRefreshButtonVisible()">
                <td>{{"Refresh page to implement changes on current page"}}</td>
            </tr>
            <tr>
                <td v-if="isRefreshButtonVisible()">
                    <DefaultButton :text="'Refresh'" @on-click="refresh"/>
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
                    <a :href="getDownloadUrl('comments')" class="button" download="dictionary.txt">
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
                    <a :href="getDownloadUrl('info_categories')" class="button" download="dictionary.txt">
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
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";
    import DefaultButton from "../element/button/DefaultButton";

    export default {
        name: "Menu",
        components: {DefaultButton},
        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                uploadMessage: ""
            }
        },

        methods: {
            isGuest() {
                return  itemViewUtil.isGuest(this.userName);
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            openUsersList() {
                routerUtil.toUserList();
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
                            .put(this.basicUrl + "/file/" + fileName + "/upload/" + this.userName, message)
                            .then(response => {
                                this.uploadMessage = response.data;
                                console.log(this.uploadMessage);
                                this.reset();
                            });
                    };
                }
            },

            refresh() {
                routerUtil.refresh();
            },

            isRefreshButtonVisible() {
                if (this.$i18n.locale === "en") {
                    return false;
                }
                return !this.isEmpty(this.uploadMessage) && !this.uploadMessage.toLowerCase().includes('not accepted');
            },

            reset() {
                const input = this.$refs.fileInput;
                input.type = 'text/plain';
                input.type = 'file';
            },

            isEmpty(message) {
                return shared.isEmpty(message);
            }
        }
    }
</script>

<style scoped>
    input[type="file"] {
        display: none;
    }
</style>