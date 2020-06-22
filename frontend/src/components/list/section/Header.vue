<template>
    <div>
        <EditableImg/>
        <div>
            <EditPanel v-if="isWishlist() || (editable && isEnglish())"
                       :item-form="item" @save="save"/>
            <div v-else class="default-margin">
                <p class="bordered">{{translate("Editing is available in English only")}}</p>
            </div>
            <div v-if="editMode && itemsManagement" class="default-margin" style="text-align: center">
                <br>
                {{translate("You can delete items here")}}<br>
                {{translate("You can only delete items you crated")}}<br>
                <br>
            </div>
        </div>
        <details v-if="itemView.header != null" class="default-margin">
            <summary class="bold">{{translate("Specification")}}</summary>
            <ItemDescription :item="item"/>
        </details>
    </div>
</template>

<script>
    import EditableImg from "../../EditableImg";
    import ItemDescription from "./ItemDescription";
    import EditPanel from "../../menu/EditPanel";
    import shared from "../../../util/shared";
    import axiosUtil from "../../../util/axiosUtil";
    import basicComponent from "../../../mixin/basicComponent";
    import view from "../../../mixin/view";
    import routerUtil from "../../../util/routerUtil";

    export default {
        name: "Header",

        components: {EditPanel, ItemDescription, EditableImg},

        mixins: [basicComponent, view],

        props: {
            item: Boolean,
            itemsManagement: Boolean,
            simpleHeader: Boolean,
            editable: Boolean
        },

        methods: {
            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                axiosUtil.updateItem(itemId, this.itemView, this.getLang(), false, null);
            },

            isEnglish() {
                return shared.isEnglish();
            },

            isWishlist() {
                return routerUtil.isWishlist(this.$route);
            }
        }
    }
</script>

<style scoped>
</style>