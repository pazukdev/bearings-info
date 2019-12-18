<template>
    <div>
        <EditPanel/>
        <table>
            <tr v-for="table in getItemsListAsTables()">
                <td>
                    <v-details v-model="table.opened">
                        {{table.items[0]}}
                        <summary><b>{{table.name}}</b></summary>
                        <table id="get-all-table">
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text">
                                        {{getFirstColumnValue(item)}}
                                    </p>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item" :user="userListView"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div class="parts-right-column-text">
                                        {{item.localizedComment}}
                                    </div>
                                </td>
                                <td class="three-column-table-button-column">
                                    <ButtonDelete :item="item"
                                                  :wishlist-view="wishlistView"
                                                  @remove-item="removeItem"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </v-details>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import EditPanel from "./EditPanel";
    import itemViewUtil from "../itemViewUtil";
    import shared from "../shared";
    import ButtonNavigateToItem from "./ButtonNavigateToItem";
    import ButtonDelete from "./button/ButtonDelete";

    export default {
        name: "ItemList",

        components: {
            ButtonDelete,
            EditPanel,
            ButtonNavigateToItem
        },

        props: {
            ordinaryItemView: Boolean,
            motorcycleCatalogueView: Boolean,
            userListView: Boolean,
            itemsManagementView: Boolean,
            wishlistView: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            })
        },

        methods: {
            getItemsListAsTables() {
                return itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
            },

            edit(editMode) {
                this.editMode = editMode === true;
            },

            getFirstColumnValue(item) {
                if (this.userListView) {
                    return item.comment;
                } else {
                    return item.location;
                }

            },

            navigateToItem(itemId) {
                this.pushTo(itemId);
            },

            removeItem(item) {
                shared.removeFromArray(item, this.itemView.partsTable.parts);
            }
        }
    }
</script>

<style scoped>

</style>