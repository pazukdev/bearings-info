<template>
    <div v-if="!isGuest()">
        <details open>
            <summary id="menu-summary">{{$t("menu")}}</summary>
            <table id="additional-menu">
                <tbody>
                    <tr>
                        <td class="three-column-table-left-column"/>
                        <td class="three-column-table-middle-column">
                            <button type="button"
                                    v-on:click="openUsersList()">
                                {{$t("users")}}
                            </button>
                        </td>
                        <td class="three-column-table-right-column"/>
                        <td class="three-column-table-button-column"/>
                    </tr>
                    <tr>
                        <td/>
                        <td>
                            <a :href="getDownloadUrl()" class="button" download="dictionary.txt">
                                {{$t("downloadDictionary")}}
                            </a>
                        </td>
                        <td/>
                        <td/>
                    </tr>
                    <tr>
                        <td/>
                        <td>
                            <label class="upload-button">
                                {{$t("uploadDictionary")}}
                                <input type="file" ref="fileInput" accept="text/plain" @change="uploadDictionary"/>
                            </label>
                        </td>
                        <td/>
                        <td/>
                    </tr>
                    <tr v-if="!isEmpty(uploadMessage)">
                        <td colspan="4">{{uploadMessage}}</td>
                    </tr>
                    <tr v-if="isRefreshButtonVisible()">
                        <td colspan="4">{{"Refresh page to implement changes on current page"}}</td>
                    </tr>
                    <tr>
                        <td/>
                        <td v-if="isRefreshButtonVisible()">
                            <DefaultButton :text="'Refresh'" @on-click="refresh"/>
                        </td>
                        <td/>
                        <td/>
                    </tr>
                </tbody>
            </table>
        </details>
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

            getDownloadUrl() {
                return this.basicUrl + "/file/dictionary/download";
            },

            uploadDictionary(event) {
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
                            .put(this.basicUrl + "/file/dictionary/upload/" + this.userName, message)
                            .then(response => {
                                this.uploadMessage = response.data;
                                console.log(this.uploadMessage);
                                this.reset();
                                // if (this.$i18n.locale !== "en") {
                                //     routerUtil.refresh();
                                // }
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