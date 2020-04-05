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
    import routerUtil from "../../util/routerUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "ItemName",

        mixins: [basicComponent, view],

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