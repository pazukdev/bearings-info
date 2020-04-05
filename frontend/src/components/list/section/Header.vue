<template>
    <div>
        <EditableImg/>
        <div>
            <EditPanel v-if="editable || isEnglish()" :item-form="item" @save="save"/>
            <div v-else class="default-margin">
                <p class="bordered">{{translate("Editing is available in English only")}}</p>
            </div>
        </div>
        <details v-if="itemView.header != null" class="default-margin" open>
            <summary>{{translate("Specification")}}</summary>
            <ItemDescription :item="item"/>
            <hr>
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

    export default {
        name: "Header",

        components: {EditPanel, ItemDescription, EditableImg},

        mixins: [basicComponent, view],

        props: {
            item: Boolean,
            simpleHeader: Boolean,
            editable: Boolean
        },

        methods: {
            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                axiosUtil.updateItem(itemId, this.itemView, this.$route.params.lang);
            },

            isEnglish() {
                return shared.isEnglish();
            }
        }
    }
</script>

<style scoped>
</style>