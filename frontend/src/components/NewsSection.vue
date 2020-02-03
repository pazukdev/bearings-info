<template>
    <div>
        <details class="default-margin" style="text-align: center" open>
            <summary>{{"News"}}</summary>

<!--                        {{itemView.adminMessage}}-->

            <div v-if="!isEmpty(itemView.adminMessage) && !isEmpty(itemView.adminMessage.text)">
                <p>{{itemView.adminMessage.text}}</p>
                <a class="simple-link" v-if="!isEmpty(itemView.adminMessage.link)"
                   :href="itemView.adminMessage.link">{{getLinkText(itemView.adminMessage)}}</a>
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

            <details open style="text-align: left" class="default-margin">
                <summary>{{"Last news"}}</summary>
                <div class="bordered" id="user-actions" v-if="itemView.userActions !== undefined">
                    <ul v-for="action in itemView.userActions">
                        <li>
                            {{action.date}}<br>
                            <router-link class="simple-link"
                                         :to="{name: 'user', params: {id: action.userId, lang: appLanguage}}">
                                {{action.userName}}
                            </router-link>
                            {{" " + action.actionType + " " + action.itemCategory.toLowerCase() + " "}}
                            <router-link class="simple-link"
                                         :to="{name: 'item', params: {id: action.itemId, lang: appLanguage}}">
                                {{action.itemName}}
                            </router-link>
                            <span v-if="!isEmpty(action.parentId) && !isEmpty(action.parentName)">
                            {{" " + $t('to') + " "}}
                            <router-link class="simple-link"
                                         :to="{name: 'item', params: {id: action.parentId, lang: appLanguage}}">
                            {{action.parentName}}
                            </router-link>
                            {{" " + $t('as') + " " + action.itemType}}
                        </span>
                        </li>
                    </ul>
                </div>
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
                itemView: state => state.dictionary.itemView,
                appLanguage: state => state.dictionary.appLanguage
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
            }
        }
    }
</script>

<style scoped>
    #user-actions {
        height: 200px;
        overflow: auto
    }

    #user-actions::-webkit-scrollbar {
        display: none;
    }

    ul {
        padding-left: 0;
    }
</style>