<template>
    <div>
        <br>
        <div style="text-align: center">
            <p v-if="!isEmpty(itemView.localizedCategory)"><b>{{itemView.localizedCategory}}</b></p>
            <p v-if="!isEmpty(itemView.localizedName)"><b>{{itemView.localizedName}}</b></p>
            <p>
                {{translate("Created by")}}
                <router-link class="simple-link"
                             v-if="showCreatorLink()"
                             :to="{name: 'user', params: {id: itemView.creatorData.id, lang:  $route.params.lang}}">
                    {{itemView.creatorData.name}}
                </router-link>
                <span v-else>{{translate("deleted user")}}</span>
            </p>
        </div>
        <br>
    </div>
</template>

<script>
    import shared from "../../util/shared";
    import {mapState} from "vuex";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import userUtil from "../../util/userUtil";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "ItemName",

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            showCreatorLink() {
                if (this.isEmpty(this.itemView.creatorData)) {
                    return false;
                }
                if (this.itemView.creatorData.status !== 'active') {
                    return this.isAdmin();
                }
                return true;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            isAdmin() {
                return userUtil.isAdmin(this.itemView);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            getLang() {
                return routerUtil.getLang(this.$route);
            }
        }
    }
</script>

<style scoped>

</style>