<template>
    <div>
        <SearchForm :items-count="itemsCount" @get-filter="getFilter"/>
        <DeletedItemsList :array="getDeletedItems()" @restore="restore"/>
        <table>
            <tbody>
            <tr>
                <td>
                    <table>
                        <tbody>
                        <tr>
                            <td class="three-column-table-left-column"/>
                            <td class="three-column-table-middle-column"/>
                            <td class="three-column-table-right-column">
                                {{translate(getCountHeaderValue())}}
                            </td>
                            <td class="three-column-table-button-column">
                                <div style="width: 32px" v-if="editMode"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()" v-if="!hideTable(table)">
                <td>
                    <v-details v-model="table.opened" v-if="isAdmin() || table.name !== 'deleted'">
                        <summary><b>{{getTextPlusCount(translate(table.name), table.items.length)}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text" v-if="!isEdit() && !translateComments">
                                        {{item.comment}}
                                    </p>
                                    <p class="three-column-table-left-column-text" v-if="!isEdit() && translateComments">
                                        {{translate(item.comment)}}
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
                                    <input v-if="isEdit()" v-model="item.secondComment" type="text"
                                           required
                                           pattern="[0-9\\.]*"
                                           title="Allowed: numbers, dot"/>
                                </td>
                                <td class="three-column-table-button-column">
                                    <ButtonDelete :item="item"
                                                  v-if="!(userListView && table.name === 'deleted')"
                                                  :wishlist-view="wishListView"
                                                  @remove-item="removeItem"/>
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
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import EditPanel from "../menu/EditPanel";
    import ListHeader from "./section/ListHeader";
    import ItemDescription from "./section/ItemDescription";
    import EditableImg from "../EditableImg";
    import SearchForm from "../form/SearchForm";
    import userUtil from "../../util/userUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";
    import DeletedItemsList from "../element/DeletedItemsList";
    import itemsList from "../../mixin/itemsList";
    import arrayUtil from "../../util/arrayUtil";

    export default {
        name: "CountedItemList",

        components: {
            DeletedItemsList,
            SearchForm,
            EditableImg,
            ItemDescription,
            ListHeader,
            EditPanel,
            ButtonNavigateToItem,
            ButtonDelete},

        props: {
            sorted: Boolean,
            userListView: Boolean,
            translateComments:Boolean,
            summaryView: Boolean,
            wishListView: Boolean,
            items: Array
        },

        mixins: [basicComponent, view, itemsList],

        data() {
            return {
                filter: "",
                itemsCount: 0
            }
        },

        methods: {
            itemsListAsTables() {
                let itemView;
                itemView = this.itemView;
                let items;
                if (this.summaryView) {
                    items = itemView.allChildren;
                } else {
                    items = this.getItems();
                }
                let opened = this.wishListView || this.userListView;
                let result = itemViewUtil.itemsListToTables(items, this.sorted, this.filter, opened);
                this.itemsCount = result.itemsCount;
                if (this.userListView && !this.editMode) {
                    for (let i = 0; i < result.tables.length; i++) {
                        result.tables[i].items = arrayUtil.sortBySecondComment(result.tables[i].items);
                    }
                }
                return result.tables;
            },

            hideTable(table) {
                if (table.name.toLowerCase() === "guest" && !userUtil.isAdmin(this.itemView)) {
                    return true;
                }
            },

            searchIsRendered() {
                return !this.editMode && !this.item;
                // return !this.item && !this.userListView;
            },

            getFilter(filter) {
                this.filter = filter;
            },

            getCountHeaderValue() {
                if (this.userListView) {
                    return "Rating";
                }
                return "Count, pcs or L"
            }
        }
    }
</script>

<style scoped>

</style>