<template>
    <div>
        <NestedItemsTableTitle :edit-mode="editMode" :replacers="false" :table="itemView.partsTable"/>
        <table id="parts-table">
            <tbody>
            <tr v-if="isShowPartsTableHeader()">
                <td>
                    <table id="parts-header">
                        <tbody>
                        <tr>
                            <td class="three-column-table-left-column">
                                {{itemView.partsTable.header[0]}}
                            </td>
                            <td class="three-column-table-middle-column">
                                {{itemView.partsTable.header[1]}}
                            </td>
                            <td class="three-column-table-right-column" v-if="itemView.partsTable.header[2] !== '-'">
                                {{itemView.partsTable.header[2]}}
                            </td>
                            <td class="three-column-table-button-column"/>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables">
                <td colspan="3">
                    <details open>
                        <summary><b>{{table.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text" v-if="!editMode">
                                        {{item.localizedComment}}
                                    </p>
                                    <input v-if="editMode" v-model="item.localizedComment" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div class="parts-right-column-text" v-if="!editMode">
                                        {{item.localizedSecondComment}}
                                    </div>
                                    <input v-if="editMode" v-model="item.localizedSecondComment" type="text"/>
                                </td>
                                <td class="three-column-table-button-column">
                                    <ButtonDelete :item="item" @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </details>
                </td>
            </tr>
            <tr>
                <td>
                    <AddPartForm :show-form="showForm" @hide-add-form="hideAddForm"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import shared from "../../util/shared";
    import AddPartForm from "../form/AddPartForm";
    import itemViewUtil from "../../util/itemViewUtil";
    import NestedItemsTableTitle from "../NestedItemsTableTitle";
    import ButtonDelete from "../element/button/ButtonDelete";

    export default {
        name: "PartList",

        components: {
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