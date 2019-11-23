<template>
    <div>
        <table id="additional-menu">
            <tbody>
            <tr>
                <td class="three-column-table-left-column"></td>
                <td class="three-column-table-middle-column">
                    <button type="button"
                            v-on:click="openItemsManagement()">
                        {{$t("itemsManagement")}}
                    </button>
                </td>
                <td class="three-column-table-right-column"></td>
                <td class="three-column-table-button-column"></td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    <button type="button"
                            v-on:click="openUsersList()">
                        {{$t("users")}}
                    </button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    <button type="button"
                            content=""
                            v-on:click="downloadDictionary">
                        {{$t("downloadDictionary")}}
                    </button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    <label class="custom-file-upload">
                        {{$t("uploadDictionary")}}
                        <input type="file" accept="text/plain" @change="uploadDictionary"/>
                    </label>
                </td>
                <td>{{uploadMessage}}</td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Menu.vue",

        props: {
            admin: Boolean,
            basicUrl: String
        },

        data() {
            return {
                uploadMessage: ""
            }
        },

        methods: {
            openItemsManagement() {
                this.$emit("open-items-management");
            },

            openUsersList() {
                this.$emit("open-users-list");
            },

            downloadDictionary() {
                axios
                    .get(this.basicUrl + "/file/dictionary/download")
                    .then(response => {
                        console.log(response);
                    });
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
                            .put(this.basicUrl + "/file/dictionary/upload", message)
                            .then(response => {
                                this.uploadMessage = "Dictionary uploaded";
                                console.log(this.uploadMessage);
                                if (this.$i18n.locale !== "en") {
                                    window.location.reload();
                                }
                            })
                            .catch(exception => {
                                this.uploadMessage = "Uploaded failed";
                                console.log(this.uploadMessage);
                            });

                    };
                }
            },
        }
    }
</script>

<style scoped>
    input[type="file"] {
        display: none;
    }

    .custom-file-upload {
        background: #808080;
        border-radius: 4px;
        border: none;
        width: 100%;
        cursor: pointer;
    }
</style>