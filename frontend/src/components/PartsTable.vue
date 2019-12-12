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
            <tr v-for="table in sortTables()">
                <td colspan="3">
                    <details open>
                        <summary><b>{{table.name}}</b></summary>
                        <table id="get-all-table">
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
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "./ButtonNavigateToItem";

    export default {
        name: "PartsTable",
        components: {ButtonNavigateToItem},
        props: {
            editMode: Boolean
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                itemsManagementId: state => state.dictionary.itemsManagementId,
                motorcycleCatalogueId: state => state.dictionary.motorcycleCatalogueId,
                wishlistId: state => state.dictionary.wishlistId,
                userlistId: state => state.dictionary.userlistId,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            sortTables() {
                let categories = [];
                let parts = this.itemView.partsTable.parts;
                for (let i = 0; i < parts.length; i++) {
                    let category = parts[i].itemCategory;
                    if (!this.$parent.isInArray(category, categories)) {
                        categories.push(category);
                    }
                }

                let nestedTables = [];
                for (let i = 0; i < categories.length; i++) {
                    let category = categories[i];

                    let nestedTable = {
                        name: category,
                        items: []
                    };

                    for (let i = 0; i < parts.length; i++) {
                        let item = parts[i];
                        if (item.itemCategory === category) {
                            nestedTable.items.push(item);
                        }
                    }

                    nestedTables.push(nestedTable);
                }

                return nestedTables;
            },

            isPartsTitleVisible() {
                return true;
                // return this.editMode;
            },

            itemHaveActiveParts() {
                for (let i = 0; i < this.itemView.partsTable.tables.length; i++) {
                    let table = this.itemView.partsTable.tables[i];
                    if (this.arrayHaveActiveItems(table.parts)) {
                        return true;
                    }
                }
                return false;
            },

            isShowPartsTableHeader() {
                return !(this.itemView.partsTable.header[0] === "-"
                    && this.itemView.partsTable.header[1] === "-"
                    && this.itemView.partsTable.header[2] === "-")
                    && this.isPartsTitleVisible();
            },

            arrayHaveActiveItems(parts) {
                return this.$parent.arrayHaveActiveItems(parts);
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
            },

            isShowQuantityValue() {
                return !this.editMode;
            },

            removeItem(item, array) {
                this.$parent.removeItem(item, array);
            }
        }
    }
</script>

<style scoped>

</style>