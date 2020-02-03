<template>
    <div>
        <details class="default-margin" style="text-align: center" open>
            <summary>{{"News"}}</summary>

<!--                        {{itemView.adminMessage}}-->

            <div v-if="!isEmpty(itemView.adminMessage) && !isEmpty(itemView.adminMessage.text)">
                <p>{{itemView.adminMessage.text}}</p>
                <a class="simple-link" v-if="!isEmpty(itemView.adminMessage.link)"
                   :href="itemView.adminMessage.link">{{getLinkText()}}</a>
            </div>

            <details v-if="isAdmin()" class="default-margin">
                <summary>{{"Publish message"}}</summary>
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <input v-model="adminMessage.text" type="text" placeholder="Message text"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input v-model="adminMessage.link" type="text" placeholder="Link"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input v-model="adminMessage.linkText" type="text" placeholder="Link text"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <DefaultButton :text="'Publish'" @on-click="publishMessage()"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </details>
        </details>
    </div>
</template>

<script>
    import axios from "axios";
    import routerUtil from "../util/routerUtil";
    import {mapState} from "vuex";
    import DefaultButton from "./element/button/DefaultButton";
    import itemViewUtil from "../util/itemViewUtil";
    import shared from "../util/shared";

    export default {
        name: "NewsSection",
        components: {DefaultButton},
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

            getLinkText() {
                let adminMessage = this.itemView.adminMessage;
                return !this.isEmpty(adminMessage.linkText) ? adminMessage.linkText : adminMessage.link;
            }
        }
    }
</script>

<style scoped>

</style>