<template>
    <div>
        <EditPanel @save="save"/>
        <table id="parts-table">
            <tbody>
            <tr>
                <td>
                    <ListHeader/>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()">
                <td>
                    <details open>
                        <summary><b>{{table.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="item in table.items">
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text" v-if="!isEdit()">
                                        {{item.localizedComment}}
                                    </p>
                                    <input v-if="isEdit()" v-model="item.localizedComment" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item" :user="userListView"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div class="parts-right-column-text" v-if="!isEdit()">
                                        {{item.localizedSecondComment}}
                                    </div>
                                    <input v-if="isEdit()" v-model="item.localizedSecondComment" type="text"/>
                                </td>
                                <td class="three-column-table-button-column">
                                    <ButtonDelete :item="item" @remove-item="removeItem"/>
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
    import itemViewUtil from "../../util/itemViewUtil";
    import ButtonDelete from "../element/button/ButtonDelete";
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import EditPanel from "../menu/EditPanel";
    import ListHeader from "./section/ListHeader";

    export default {
        name: "ItemList",
        components: {ListHeader, EditPanel, ButtonNavigateToItem, ButtonDelete},

        props: {
            editableComments: Boolean,
            userListView: Boolean
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
                return itemViewUtil.itemsListToTables(this.itemView.partsTable.parts);
            },

            save() {
                this.update(this.itemView.itemId);
            },

            update(itemId) {
                let basicUrl = this.basicUrl.toString();
                let userName = this.userName.toString();
                let appLanguage = this.appLanguage.toString();
                itemViewUtil.updateItem(itemId, basicUrl, userName, appLanguage);
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