<template>
    <div>
        <table id="parts-table">
            <tbody>
            <tr v-if="searchIsRendered()">
                <td>
                    <input type="search" v-model="filter" placeholder="Search...">
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()" v-if="!hideTable(table)">
                <td>
                    <v-details v-model="table.opened">
                        <summary><b>{{table.name}}</b></summary>

                        <table>
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
                    </v-details>
                </td>
            </tr>
            </tbody>
        </table>
        <hr>
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

    export default {
        name: "CountedItemList",

        components: {
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
            summaryView: Boolean,
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
                filter: ""
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
                // let opened = !this.summaryView;
                let opened = false;
                return itemViewUtil.itemsListToTables(items, this.sorted, this.filter, opened);
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
            },

            searchIsRendered() {
                return !this.editMode && !this.item;
                // return !this.item && !this.userListView;
            }
        }
    }
</script>

<style scoped>

</style>