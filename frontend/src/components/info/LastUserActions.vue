<template>
    <div>
        <details style="text-align: left" class="default-margin">
            <summary>{{translate("Last 10 new " + itemType)}}</summary>
            <ul class="bordered" id="user-actions"
                v-if="userActions !== undefined && userActions.length > 0">
                <li v-for="action in userActions">
                    {{action.date}}<br>
                    <router-link class="simple-link"
                                 :to="{name: 'user', params: {id: action.userId, lang: $route.params.lang}}">
                        {{action.userName}}
                    </router-link>
                    {{" " + translate(action.actionType) + " " + translate(action.itemCategory.toLowerCase()) + " "}}
                    <router-link class="simple-link"
                                 :to="{name: 'item', params: {id: action.itemId, lang: $route.params.lang}}">
                        {{action.itemName}}
                    </router-link>
                    <span v-if="!isEmpty(action.parentId) && !isEmpty(action.parentName)">
                            {{" " + translate('to') + " "}}
                            <router-link class="simple-link"
                                         :to="{name: 'item', params: {id: action.parentId, lang: $route.params.lang}}">
                            {{action.parentName}}
                            </router-link>
                            {{" " + translate('as') + " " + translate(action.itemType)}}
                        </span>
                </li>
            </ul>
            <p v-else class="bordered">{{translate("No users activity")}}</p>
        </details>
    </div>
</template>

<script>
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: "LastUserActions",

        props: {userActions:Array, itemType:String},

        methods: {
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
    #user-actions {
        max-height: 200px;
        overflow: auto;
        list-style-position: inside;
    }

    #user-actions::-webkit-scrollbar {
        display: none;
    }
</style>