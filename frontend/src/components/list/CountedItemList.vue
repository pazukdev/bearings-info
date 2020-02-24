<template>
    <div>
        <SearchForm :items-count="itemsCount" @get-filter="getFilter"/>
        <table>
            <tbody>
            <tr v-for="table in itemsListAsTables()" v-if="!hideTable(table)">
                <td>
                    <v-details v-model="table.opened" v-if="isAdmin() || table.name !== 'deleted'">
                        <summary><b>{{translate(table.name)}}</b></summary>
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
    import EditableImg from "../EditableImg";
    import SearchForm from "../form/SearchForm";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import shared from "../../util/shared";

    export default {
        name: "CountedItemList",

        components: {
            SearchForm,
            EditableImg,
            ItemDescription,
            ListHeader,
            EditPanel,
            ButtonNavigateToItem,
            ButtonDelete},

        props: {
            item: Boolean,
            editableComments: Boolean,
            userListView: Boolean,
            translateComments:Boolean,
            summaryView: Boolean,
            wishListView: Boolean,
            itemViewProp: Object,
            sorted: Boolean,
            items: Array
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
                    items = itemView.children;
                }
                let opened = this.wishListView;
                let result = itemViewUtil.itemsListToTables(items, this.sorted, this.filter, opened);
                this.itemsCount = result.itemsCount;
                return result.tables;
            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
            },

            isEdit() {
                return this.editableComments && this.editMode;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            hideTable(table) {
                if (table.name.toLowerCase() === "guest" && !itemViewUtil.isAdmin(this.itemView)) {
                    return true;
                }
            },

            searchIsRendered() {
                return !this.editMode && !this.item;
                // return !this.item && !this.userListView;
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            getFilter(filter) {
                this.filter = filter;
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>