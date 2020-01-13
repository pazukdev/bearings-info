<template>
    <div>
        <Header :item="true"/>
        <NestedItemsTableTitle v-if="itemView.partsEnabled"
                               :edit-mode="editMode" :replacers="false" :table="itemView.partsTable"/>
        <ItemList :item="true" :editable-comments="true" :sorted="!editMode"/>
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
    import ItemList from "./ItemList";
    import Header from "./section/Header";

    export default {
        name: "PartList",

        components: {
            Header,
            ItemList,
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
                return itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
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
                return shared.isNestedItemsTitleVisible(this.itemView.partsTable.parts, this.editMode);
            },

            isShowPartsTableHeader() {
                let header = this.itemView.partsTable.header;
                if (header === null || !this.isTitleVisible()) {
                    return false;
                }
                return !shared.isInArray("-", header);
            },

            hideAddForm() {
                this.$emit("hide-add-form");
            },

            removeItem(item) {
                shared.removeFromArray(item, this.itemView.partsTable.parts);
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