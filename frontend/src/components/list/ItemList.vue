<template>
    <div>
        <table id="parts-table">
            <tbody>
            <tr v-if="!usageView">
                <td>
                    <ListHeader/>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()" v-if="!hideTable(table)">
                <td>
                    <v-details v-model="table.opened">
                        <summary><b>{{table.name}}</b></summary>

                        <table v-if="!itemsManagementView">
                            <tbody>
                            <tr v-for="item in table.items">
<!--                                {{item}}-->
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text" v-if="!isEdit()">
                                        {{item.comment}}
                                    </p>
                                    <input v-if="isEdit()" v-model="item.comment" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item" :user="userListView"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <p class="parts-right-column-text" v-if="!isEdit()">
                                        {{item.secondComment}}
                                    </p>
                                    <input v-if="isEdit()" v-model="item.secondComment" type="text"/>
                                </td>
                                <td class="three-column-table-button-column">
                                    <ButtonDelete :item="item" @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <table v-if="itemsManagementView">
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="two-columns-table-left-column" style="text-align: left">
                                    <p>{{item.comment}}</p>
                                </td>
                                <td class="two-column-table-right-column">
                                    <ButtonNavigateToItem :part="item" :user="userListView"/>
                                </td>
                                <td>
                                    <ButtonDelete :item="item" @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonDelete from "../element/button/ButtonDelete";
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import EditPanel from "../menu/EditPanel";
    import ListHeader from "./section/ListHeader";
    import ItemDescription from "./section/ItemDescription";
    import NestedItemsTableTitle from "./section/NestedItemsTableTitle";
    import EditableImg from "../EditableImg";

    export default {
        name: "ItemList",
        components: {
            EditableImg,
            NestedItemsTableTitle,
            ItemDescription,
            ListHeader,
            EditPanel,
            ButtonNavigateToItem,
            ButtonDelete},

        props: {
            item: Boolean,
            editableComments: Boolean,
            userListView: Boolean,
            itemsManagementView: Boolean,
            usageView: Boolean,
            itemViewProp: Object,
            sorted: Boolean
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            itemsListAsTables() {
                let itemView;
                if (this.usageView && this.itemViewProp != null) {
                    itemView = this.itemViewProp;
                } else {
                    itemView = this.itemView;
                }

                let tables = itemViewUtil.itemsListToTables(itemView.partsTable.parts, this.sorted);
                if (this.itemsManagementView) {
                    for (let i = 0; i < tables.length; i++) {
                        tables[i].opened = false;
                    }
                }
                return tables;
            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
            },

            isEdit() {
                return this.editableComments && this.editMode;
            },

            hideTable(table) {
                if (table.name.toLowerCase() === "guest" && !itemViewUtil.isAdmin(this.itemView)) {
                    return true;
                }
            }
        }
    }
</script>

<style scoped>
    p {
        /*text-align: left;*/
    }
</style>