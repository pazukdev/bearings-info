<template>
    <div>
        <details class="default-margin" style="text-align: center">
            <summary>{{translate("News")}}</summary>
            <div v-if="!isEmpty(itemView.adminMessage) && !isEmpty(itemView.adminMessage.localizedText)">
                <p>{{itemView.adminMessage.localizedText}}</p>
                <a class="simple-link" v-if="!isEmpty(itemView.adminMessage.link)"
                   :href="itemView.adminMessage.link">{{getLinkText(itemView.adminMessage)}}</a>
            </div>

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
                            <input v-model="adminMessage.link" type="text" :placeholder="translate('Link')"/>
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
            <LastUserActions :user-actions="itemView.lastVehicles" :item-type="'vehicles'"/>
            <LastUserActions :user-actions="itemView.lastReplacers" :item-type="'replacers'"/>
        </details>
    </div>
</template>

<script>
    import DefaultButton from "../element/button/DefaultButton";
    import LastUserActions from "./LastUserActions";
    import axios from "axios";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import itemViewUtil from "../../util/itemViewUtil";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";
    import {mapState} from "vuex";

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
                return itemViewUtil.isAdmin(this.itemView);
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            getLinkText(source) {
                return !this.isEmpty(source.linkText) ? source.linkText : source.link;
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>