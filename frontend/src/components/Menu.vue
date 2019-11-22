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
                            v-on:click="downloadDictionary()">
                        {{"Download dictionary"}}
                    </button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    <input type="file"
                           style="color: black"
                           @change="uploadDictionary()">
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Menu.vue",

        props: {
            admin: Boolean
        },

        methods: {
            openItemsManagement() {
                this.$emit("open-items-management");
            },

            openUsersList() {
                this.$emit("open-users-list");
            },

            downloadDictionary() {
                this.$emit("download-dictionary");
            },

            uploadDictionary() {
                this.$emit("upload-dictionary");
            }
        }
    }
</script>

<style></style>