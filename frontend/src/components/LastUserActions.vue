<template>
    <div>
        <details style="text-align: left" class="default-margin">
            <summary>{{"Last 10 new " + itemType}}</summary>
            <ul class="bordered" id="user-actions"
                v-if="userActions !== undefined && userActions.length > 0">
                <li v-for="action in userActions">
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
            <p v-else class="bordered">{{"No users activity"}}</p>
        </details>
    </div>
</template>

<script>
    import shared from "../util/shared";
    import {mapState} from "vuex";

    export default {
        name: "LastUserActions",

        props: {userActions:Array, itemType:String},

        computed: {
            ...mapState({
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            isEmpty(value) {
                return shared.isEmpty(value);
            },
        }
    }
</script>

<style scoped>
    #user-actions {
        max-height: 200px;
        overflow: auto;
        list-style-position: inside;
    }

    #user-actions::-webkit-scrollbar {
        display: none;
    }
</style>