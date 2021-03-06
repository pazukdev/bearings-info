<template>
    <div v-if="item">
        <DeletedItemsList :array="getDeletedRows()" :row="true" @restore="restore"/>
        <table id="item-description">
            <tbody>
            <tr v-for="row in sortByWeight(getRows())">
                <td class="two-columns-table-left-column">
                    <table style="border-spacing: 0px" v-if="!rowParamIsEditable(row) && row.ids.length > 0">
                        <tr v-for="(id, index) in row.ids">
                            <td>
                                <p v-if="index === 0">{{row.parameter}}</p>
                                <p v-else>{{row.parameter + " #" + (index + 1)}}</p>
                            </td>
                        </tr>
                    </table>
                    <input v-if="rowParamIsEditable(row)" v-model="row.parameter" type="text" required
                           pattern="[a-zA-Zа-яА-Я0-9 +()_\\.,/-]*"
                           title="Allowed: letters, numbers, -, _, /, +, (, ), dot, comma, space"/>
                </td>
                <td class="two-column-table-right-column">
                    <input v-if="isEdit()" v-model="row.value" type="text" required
                           pattern='[a-zA-Zа-яА-Я0-9 +№_\\.;",/-]*'
                           title="Allowed: letters, numbers, -, +, _, /, ;, №, dot, comma, space"/>
                    <table v-else-if="!isEdit() && row.ids.length > 0">
                        <tr v-for="(id, index) in row.ids">
                            <td>
                                <router-link class="simple-link" v-if="!isEmpty(id)"
                                             :to="{name: 'item', params: {id: id, lang: lang}}">
                                    {{row.value.split("; ")[index]}}
                                </router-link>
                                <p v-else>{{row.value.split("; ")[index]}}</p>
                            </td>
                        </tr>
                    </table>
                    <div v-else>
                        {{translate(row.value)}}
                    </div>
                </td>
                <td>
                    <ButtonDelete v-if="rowIsDeletable(row)" :item="row" @remove-item="remove"/>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="alert-message">
                    {{newHeaderRowMessage}}
                </td>
            </tr>
            <tr v-if="isEdit()">
                <td colspan="3">
                    <div style="margin: 10px 0; border-top: solid 1px grey">
                        <div style="margin: 8px 0">
                            {{translate("Add new parameter")}}
                        </div>
                    </div>
                </td>
            </tr>
            <tr v-if="isEdit()">
                <td>
                    <input v-model="parameter" type="text" pattern="[a-zA-Zа-яА-Я0-9_\\. ,/-]*"
                           :placeholder="translate('New parameter')"
                           title="Allowed: letters, numbers, - , _ , / , dot, comma, space"/>
                </td>
                <td>
                    <input v-model="value" type="text" pattern="[a-zA-Zа-яА-Я0-9_\\. ;,/-]*"
                           :placeholder="translate('New value')"
                           title="Allowed: letters, numbers, - , _ , / , ; , dot, comma, space"/>
                </td>
                <td>
                    <ButtonAdd v-if="!isEmpty(parameter)"
                               @add-item="addHeaderRow"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import shared from "../../../util/shared";
    import ButtonNavigateToItem from "../../element/button/ButtonNavigateToItem";
    import ButtonDelete from "../../element/button/ButtonDelete";
    import ListHeader from "./ListHeader";
    import ButtonAdd from "../../element/button/ButtonAdd";
    import arrayUtil from "../../../util/arrayUtil";
    import basicComponent from "../../../mixin/basicComponent";
    import view from "../../../mixin/view";
    import DeletedItemsList from "../../element/DeletedItemsList";

    export default {
        name: "ItemDescription",

        components: {
            DeletedItemsList,
            ButtonAdd,
            ListHeader,
            ButtonDelete,
            ButtonNavigateToItem
        },

        props: {
            item: Boolean
        },
        
        mixins: [basicComponent, view],

        data() {
            return {
                newHeaderRowMessage: "",
                parameter: "",
                value: "",
                notDeletableRows: [
                    "Category",
                    "Name"
                ],
                notEditableParams: [
                    "Category",
                    "Name",
                    "Class",
                    "Production",
                    "Manufacturer",
                    "Full name",
                    "Size, mm",
                    "Weight, kg",
                    "Weight, g",
                    "Fuel capacity, L",
                    "Max speed, kmh"
                ],
                deletedRows: []
            }
        },

        methods: {
            sortByWeight(array) {
                return arrayUtil.sortByWeight(array);
            },

            addHeaderRow() {
                this.newHeaderRowMessage = "";
                if (this.rowIsInList(this.parameter)) {
                    this.newHeaderRowMessage = "Parameter already exists"
                } else {
                    let newHeaderRow = {
                        id: "",
                        name: this.parameter,
                        localizedName: this.parameter,
                        status: "added",
                        parameter: this.parameter,
                        value: this.value,
                        itemId: "",
                        message: "",
                        ids: [null]
                    };

                    this.getRows().push(newHeaderRow);

                    this.parameter = "";
                    this.value = "";
                }
            },

            newLineIsEmpty(parameter, value) {
                return parameter === "" || value === "";
            },

            rowIsInList(param) {
                let array = this.getRows();
                for (let i=0; i < array.length; i++) {
                    if (array[i].parameter === param) {
                        return true;
                    }
                }
                return false;
            },

            isShowInfoButton(itemId) {
                return itemId !== "-" && !this.editMode;
            },

            isRemoveHeaderRowButtonVisible(deletable) {
                return this.editMode && deletable;
            },

            remove(row) {
                shared.removeFromArray(row, this.getRows());
                this.getDeletedRows().push(row);
            },

            restore(row) {
                shared.removeFromArray(row, this.getDeletedRows());
                this.getRows().push(row);
            },

            getRows() {
                return this.itemView.header.rows;
            },

            getDeletedRows() {
                return this.deletedRows;
            },

            rowParamIsEditable(row) {
                if (!this.rowIsDeletable(row)) {
                    return false;
                }
                if ((!this.isAdmin() && !this.isEditor())
                    && shared.isInArray(row.name, this.notEditableParams)) {
                    return false;
                }
                return this.isEdit();
            },

            rowIsDeletable(row) {
                if (shared.isInArray(row.name, this.notDeletableRows)) {
                    return false;
                }
                if (this.isEditable(row)) {
                    return true;
                }
                return this.isEdit();
            },

            isEdit() {
                return this.item && this.editMode;
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
        border-collapse: initial;
        border-spacing: 4px;
        border-style: hidden;
    }
</style>