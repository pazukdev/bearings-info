<template>
    <div>
<!--        {{itemView.children}}<br>-->
<!--        {{itemView.idsToRemove}}-->
        <CountedItemList :item="true" :editable-comments="true" :sorted="!editMode"/>
        <AddPartForm :show-form="showForm" @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import shared from "../../util/shared";
    import AddPartForm from "../form/AddPartForm";
    import itemViewUtil from "../../util/itemViewUtil";
    import NestedItemsTableTitle from "./section/NestedItemsTableTitle";
    import ButtonDelete from "../element/button/ButtonDelete";
    import ListHeader from "./section/ListHeader";
    import Header from "./section/Header";
    import CountedItemList from "./CountedItemList";

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

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            }),

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
            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
                // shared.removeFromArray(item, this.itemView.children);
                this.$emit("show-add-form");
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