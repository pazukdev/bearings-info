<template>
    <div>
        <details class="default-margin" style="text-align: center">
            <summary>{{translate("News")}}</summary>
            <details v-if="isAdmin()" class="default-margin">
                <summary>{{translate('Publish message')}}</summary>
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <input v-model="adminMessage.text" type="text" :placeholder="translate('Message text')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input v-model="adminMessage.url" type="text" :placeholder="translate('Link')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input v-model="adminMessage.linkText" type="text" :placeholder="translate('Link text')"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <DefaultButton :text="translate('Publish')" @on-click="publishMessage()"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </details>
            <LastUserActions :user-actions="itemView.lastVehicles" :note="'vehicles'"/>
            <LastUserActions :user-actions="itemView.lastReplacers" :note="'replacers'"/>
        </details>
    </div>
</template>

<script>
    import DefaultButton from "../element/button/DefaultButton";
    import LastUserActions from "./LastUserActions";
    import axios from "axios";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";
    import {mapState} from "vuex";
    import userUtil from "../../util/userUtil";

    export default {
        name: "NewsSection",
        components: {LastUserActions, DefaultButton},
        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                adminMessage: {
                    text: "",
                    link: "",
                    linkText: "",
                }
            }
        },

        methods: {
            publishMessage() {
                axios
                    .post(this.basicUrl
                        + "/" + "info"
                        + "/" + "create-admin-message",
                        this.adminMessage, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        routerUtil.refresh();
                    })
            },

            isAdmin() {
                return userUtil.isAdmin();
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>