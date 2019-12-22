<template>
    <div>
        <EditPanel @save="save"/>
        <table id="parts-table">
            <tbody>
            <tr v-if="isShowPartsTableHeader()">
                <td>
                    <table id="parts-header">
                        <tbody>
                        <tr>
                            <td class="three-column-table-left-column">
                                {{itemView.partsTable.header[0]}}
                            </td>
                            <td class="three-column-table-middle-column">
                                {{itemView.partsTable.header[1]}}
                            </td>
                            <td class="three-column-table-right-column" v-if="itemView.partsTable.header[2] !== '-'">
                                {{itemView.partsTable.header[2]}}
                            </td>
                            <td class="three-column-table-button-column"/>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr v-for="table in itemsListAsTables()">
                <td colspan="3">
                    <details open>
                        <summary><b>{{table.name}}</b></summary>
                        <table>
                            <tbody>
                            <tr v-for="item in table.items">
<!--                                {{item}}-->
                                <td class="three-column-table-left-column">
                                    <p class="three-column-table-left-column-text" v-if="!editMode">
                                        {{item.localizedComment}}
                                    </p>
                                    <input v-if="editMode" v-model="item.localizedComment" type="text"/>
                                </td>
                                <td class="three-column-table-middle-column">
                                    <ButtonNavigateToItem :part="item"/>
                                </td>
                                <td class="three-column-table-right-column">
                                    <div class="parts-right-column-text" v-if="!editMode">
                                        {{item.localizedSecondComment}}
                                    </div>
                                    <input v-if="editMode" v-model="item.localizedSecondComment" type="text"/>
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
    import shared from "../../util/shared";
    import {mapState} from "vuex";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import axios from "axios";
    import EditPanel from "../menu/EditPanel";

    export default {
        name: "EditableItemList",
        components: {EditPanel, ButtonNavigateToItem, ButtonDelete},

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
            },

            removeItem(item) {
                itemViewUtil.removeItemFromItemList(this.itemView, item);
            },

            isShowPartsTableHeader() {
                let header = this.itemView.partsTable.header;
                if (header === null) {
                    return false;
                }
                return !shared.isInArray("-", header);
            },
        }
    }
</script>

<style scoped>

</style>