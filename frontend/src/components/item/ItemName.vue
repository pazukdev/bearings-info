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
            <p>
                {{getAdditionalInfo(itemView)}}
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
            },

            getAdditionalInfo(itemView) {
                let category = itemView.category;
                let requiredCategory = category === "Bearing" || category === "Seal";
                if (requiredCategory && !this.isEmpty(itemView.header)) {
                    for (let i = 0; i <= itemView.header.rows.length; i++) {
                        let row = itemView.header.rows[i];
                        if (row.name === "Size, mm") {
                            if (!this.isEmpty(row.value)) {
                                return "(" + row.value + ")";
                            }
                        }
                    }
                }
                return "";
            },
        }
    }
</script>

<style scoped>

</style>