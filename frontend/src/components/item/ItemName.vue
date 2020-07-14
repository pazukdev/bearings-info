<template>
    <div>
        <div style="text-align: center">
            <p id="item-localized-category"
               v-if="!isEmpty(itemView.localizedCategory)">
                {{itemView.localizedCategory}}
            </p>
            <p id="item-localized-name"
               v-if="!isEmpty(itemView.localizedName)">
                {{itemView.localizedName}}
            </p>
            <p v-if="item">
                {{translate("Created by")}}
                <router-link class="simple-link"
                             v-if="showCreatorLink()"
                             :to="{name: 'user', params: {id: itemView.creatorData.id, lang:  $route.params.lang}}">
                    {{itemView.creatorData.name}}
                </router-link>
                <span v-else>{{translate("deleted user")}}</span>
            </p>
        </div>
    </div>
</template>

<script>
    import routerUtil from "../../util/routerUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "ItemName",

        mixins: [basicComponent, view],

        props: {
            item: Boolean
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

            getLang() {
                return routerUtil.getLang(this.$route);
            }
        }
    }
</script>

<style scoped>

</style>