<template>
    <div>
<!--        {{itemView.partsTable.parts}}-->
        <table id="parts-table">
            <tbody>
            <tr v-if="isPartsTitleVisible()">
                <td>
                    {{itemView.partsTable.localizedName}}
                </td>
            </tr>
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
                            <td class="three-column-table-button-column"></td>
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
                                    <p class="three-column-table-left-column-text"
                                       v-if="!editMode">
                                        {{item.location}}
                                    </p>
                                    <input v-if="editMode"
                                           v-model="item.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div v-if="isShowQuantityValue()" class="parts-right-column-text">
                                        {{item.quantity}}
                                    </div>
                                    <input v-if="editMode" v-model="item.quantity" type="text"/>
                                </td>
                                <td class="three-column-table-button-column" v-if="editMode">
                                    <button v-model="itemView"
                                            type="button"
                                            class="round-button"
                                            style="background: red"
                                            @click="removeItem(item, table.items)">
                                        {{"-"}}
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </details>
                </td>
            </tr>
            <tr>
                <td>
                    <AddPartForm :edit-mode="editMode" :show-form="showForm" @hide-add-form="hideAddForm"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "./ButtonNavigateToItem";
    import shared from "../shared";
    import AddPartForm from "./AddPartForm";

    export default {
        name: "PartsTable",

        components: {
            ButtonNavigateToItem,
            AddPartForm
        },

        props: {
            editMode: Boolean,
            showForm: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            }),

            itemsListAsTables() {
                return shared.itemsListToTables(this.itemView.partsTable.parts);
            }
        },

        data() {
            return {
                message: "",
                newItem: {
                    comment: ""
                }
            }
        },

        methods: {
            isPartsTitleVisible() {
                return this.itemView.partsTable.parts.length > 0 || this.editMode;
            },

            isShowPartsTableHeader() {
                return !(this.itemView.partsTable.header[0] === "-"
                    && this.itemView.partsTable.header[1] === "-"
                    && this.itemView.partsTable.header[2] === "-")
                    && this.isPartsTitleVisible();
            },

            isShowQuantityValue() {
                return !this.editMode;
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