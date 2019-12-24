<template>
    <div>
        <ItemViewImage/>
        <ItemDescription :item="item"/>
        <EditPanel @save="save"/>
        <NestedItemsTableTitle :edit-mode="editMode" :replacers="false" :table="itemView.partsTable"/>
        <table id="parts-table">
            <tbody>
            <tr>
                <td>
                    <ListHeader/>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()">
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
                                    <div class="parts-right-column-text" v-if="!isEdit()">
                                        {{item.secondComment}}
                                    </div>
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
    import ItemViewImage from "../item/ItemViewImage";

    export default {
        name: "ItemList",
        components: {
            ItemViewImage,
            NestedItemsTableTitle, ItemDescription, ListHeader, EditPanel, ButtonNavigateToItem, ButtonDelete},

        props: {
            item: Boolean,
            editableComments: Boolean,
            userListView: Boolean,
            itemsManagementView: Boolean
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
                let tables = itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
                if (this.itemsManagementView) {
                    for (let i = 0; i < tables.length; i++) {
                        tables[i].opened = false;
                    }
                }
                return tables;
            },

            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                let itemView = this.itemView;
                let basicUrl = this.basicUrl.toString();
                let userName = this.userName.toString();
                let appLanguage = this.appLanguage.toString();
                let authorization = this.authorization;
                itemViewUtil.updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization, this.$store);
            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
            },

            isEdit() {
                return this.editableComments && this.editMode;
            }
        }
    }
</script>

<style scoped>

</style>