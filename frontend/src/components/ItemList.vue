<template>
    <div>
        <EditPanel v-if="!motorcycleCatalogueView" @save="save"/>
        <table>
            <tr v-for="table in getItemsListAsTables()">
                <td>
                    <v-details v-model="table.opened">
<!--                        {{table.items[0]}}-->
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
    import ButtonNavigateToItem from "./button/ButtonNavigateToItem";
    import ButtonDelete from "./button/ButtonDelete";
    import axios from "axios";

    export default {
        name: "ItemList",

        components: {
            ButtonDelete,
            EditPanel,
            ButtonNavigateToItem
        },

        props: {
            motorcycleCatalogueView: Boolean,
            userListView: Boolean,
            itemsManagementView: Boolean,
            wishlistView: Boolean
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
            getItemsListAsTables() {
                let tables = itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
                if (this.itemsManagementView) {
                    for (let i = 0; i < tables.length; i++) {
                        tables[i].opened = false;
                    }
                }
                return tables;
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
                this.itemView.idsToRemove.push(item.itemId);
            },

            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                axios
                    .put(this.basicUrl.toString()
                        + "/" + "item"
                        + "/" + "update"
                        + "/" + itemId
                        + "/" + this.userName.toString()
                        + "/" + this.appLanguage.toString(),
                        this.itemView, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        let updatedItemView = response.data;
                        itemViewUtil.dispatchView(this.$store, updatedItemView);
                        console.log("item updated");
                    });
            }
        }
    }
</script>

<style scoped>

</style>