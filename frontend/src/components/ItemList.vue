<template>
    <div>
        <EditPanel @cancel="cancel" @edit="edit" @save="save"/>

        <table>
            <tr v-for="table in itemView.partsTable.tables" v-if="arrayHaveActiveItems(table.parts)">
                <td colspan="3">
                    <v-details v-model="table.opened">
                        <summary><b>{{table.localizedName}}</b></summary>
                        <table id="get-all-table">
                            <tbody>
                            <tr v-for="part in table.parts" v-if="statusIsActive(part.status)">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text"
                                       v-if="!editMode || (editMode && !ordinaryItemView)">
                                        {{getFirstColumnValue(part)}}
                                    </p>
                                    <input v-if="editMode && ordinaryItemView"
                                           v-model="part.location" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <p v-if="userListView">
                                        {{part.buttonText}}
                                    </p>
                                    <button type="button"
                                            v-if="!userListView"
                                            @click="navigateToItem(part.itemId)">
                                        {{part.buttonText}}
                                    </button>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div v-if="showQuantityValue" class="parts-right-column-text">
                                        {{part.quantity}}
                                    </div>
                                    <div v-if="motorcycleCatalogueView || itemsManagementView"
                                         class="parts-right-column-text">
                                        {{part.localizedComment}}
                                    </div>
                                    <input v-if="editMode && ordinaryItemView"
                                           v-model="part.quantity" type="text"/>
                                </td>
                                <td class="three-column-table-button-column" v-if="editMode && part.comment !== 'Admin'">
                                    <button v-model="itemView"
                                            v-if="isItemDeleteButtonVisibleToCurrentUser(part)"
                                            type="button"
                                            class="round-button"
                                            style="background: red"
                                            @click="removePartFromList(part, table.parts)">
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

    export default {
        name: "ItemList",

        components: {
            EditPanel
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

        data() {
            return {
                editMode: false
            }
        },

        methods: {
            arrayHaveActiveItems(array) {
                for (let i=0; i < array.length; i++) {
                    if (this.statusIsActive(array[i].status)) {
                        return true;
                    }
                }
                return false;
            },

            statusIsActive(status) {
                return status === "active";
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

            removePartFromList(part, array) {
                this.removeFromArray(part, array);
                if (this.itemsManagementView || this.wishListView || this.userListView) {
                    this.itemView.idsToRemove.push(part.itemId);
                }
            },

            removeFromArray(element, array) {
                array.splice(array.indexOf(element), 1);
            },

            isItemDeleteButtonVisibleToCurrentUser(item) {
                return this.itemView.userData.comment === "Admin"
                    || this.currentUserIsCreator(item)
                    || this.ordinaryItemView
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