<template>
    <div>
        <EditPanel @cancel="cancel" @edit="edit" @save="save"/>

        <table>
            <tr v-for="table in getItemsListAsTables()">
                <td>
                    <v-details v-model="table.opened">
                        <summary><b>{{table.name}}</b></summary>
                        <table id="get-all-table">
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text"
                                       v-if="!editMode">
                                        {{getFirstColumnValue(item)}}
                                    </p>
                                    <input v-if="editMode && ordinaryItemView"
                                           v-model="item.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <p v-if="userListView">
                                        {{item.buttonText}}
                                    </p>
                                    <ButtonNavigateToItem v-if="!userListView" :part="item"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div v-if="showQuantityValue" class="parts-right-column-text">
                                        {{item.quantity}}
                                    </div>
                                    <div v-if="motorcycleCatalogueView || itemsManagementView"
                                         class="parts-right-column-text">
                                        {{item.localizedComment}}
                                    </div>
                                    <input v-if="editMode && ordinaryItemView"
                                           v-model="item.quantity" type="text"/>
                                </td>
                                <td class="three-column-table-button-column" v-if="editMode && item.comment !== 'Admin'">
                                    <button v-if="isItemDeleteButtonVisibleToCurrentUser(item)"
                                            type="button"
                                            class="round-button"
                                            style="background: red"
                                            @click="removeItem(item)">
                                        {{"-"}}
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <p></p>
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

    export default {
        name: "ItemList",

        components: {
            EditPanel,
            ButtonNavigateToItem
        },

        props: {
            ordinaryItemView: Boolean,
            motorcycleCatalogueView: Boolean,
            userListView: Boolean,
            itemsManagementView: Boolean,
            wishlistView: Boolean,
            showQuantityValue: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                editMode: false
            }
        },

        methods: {
            getItemsListAsTables() {
                return itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
            },

            cancel() {
                this.editMode = false;
            },

            edit(editMode) {
                this.editMode = editMode === true;
            },

            save() {
                this.editMode = false;
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

            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}/:lang` });
            },

            removeItem(item) {
                shared.removeFromArray(item, this.itemView.partsTable.parts);
            },

            isItemDeleteButtonVisibleToCurrentUser(item) {
                return this.itemView.userData.comment === "Admin"
                    || this.currentUserIsCreator(item)
                    || this.wishListView;
            },

            currentUserIsCreator(item) {
                this.text = item;
                return item.creatorName === this.userName;
            },
        }
    }
</script>

<style scoped>

</style>