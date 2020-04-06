<template>
    <div>
        <CountedItemList :item="true" :editable-comments="true" :sorted="!editMode"/>
        <AddPartForm :show-form="showForm" @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import shared from "../../util/shared";
    import AddPartForm from "../form/AddPartForm";
    import itemViewUtil from "../../util/itemViewUtil";
    import NestedItemsTableTitle from "./section/NestedItemsTableTitle";
    import ButtonDelete from "../element/button/ButtonDelete";
    import ListHeader from "./section/ListHeader";
    import Header from "./section/Header";
    import CountedItemList from "./CountedItemList";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "PartList",

        components: {
            CountedItemList,
            Header,
            ListHeader,
            ButtonDelete,
            NestedItemsTableTitle,
            ButtonNavigateToItem,
            AddPartForm
        },

        props: {
            showForm: Boolean
        },

        mixins: [basicComponent, view],

        computed: {
            itemsListAsTables() {
                return itemViewUtil.itemsListToTables(this.itemView.children);
            }
        },

        data() {
            return {
                message: "",
                titleVisible: ""
            }
        },

        methods: {
            isTitleVisible() {
                return shared.isNestedItemsTitleVisible(this.itemView.children, this.editMode);
            },

            hideAddForm() {
                this.$emit("hide-add-form");
            }
        }
    }
</script>

<style scoped>
    table, tr {
        width: 100%;
        padding: 0;
        margin: 0;
    }

    * {
        margin: 0;
        padding: 0;
    }
</style>