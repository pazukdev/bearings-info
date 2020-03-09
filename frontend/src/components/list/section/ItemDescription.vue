<template>
    <div v-if="item">
        <table id="item-description">
            <tbody>
            <tr v-for="row in sortByWeight(itemView.header.rows)">
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
                           pattern="[a-zA-Zа-яА-Я0-9_\\. ,/-]*"
                           title="Allowed: letters, numbers, - , _ , / , dot, comma, space"/>
                </td>
                <td class="two-column-table-right-column">
                    <input v-if="isEdit()" v-model="row.value" type="text" required
                           pattern="[a-zA-Zа-яА-Я0-9_\\. ;,/-]*"
                           title="Allowed: letters, numbers, - , _ , / , ; , dot, comma, space"/>
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
                        {{row.value}}
                    </div>
                </td>
                <td>
                    <ButtonDelete v-if="rowIsDeletable(row)" :item="row" @remove-item="removeItem"/>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="alert-message">
                    {{newHeaderRowMessage}}
                </td>
            </tr>
            <tr v-if="isEdit()">
                <td>
                    <input v-model="parameter" type="text" pattern="[a-zA-Zа-яА-Я0-9_\\. ,/-]*"
                           title="Allowed: letters, numbers, - , _ , / , dot, comma, space"/>
                </td>
                <td>
                    <input v-model="value" type="text" pattern="[a-zA-Zа-яА-Я0-9_\\. ;,/-]*"
                           title="Allowed: letters, numbers, - , _ , / , ; , dot, comma, space"/>
                </td>
                <td>
                    <ButtonAdd v-if="!isEmpty(parameter)" @add-item="addHeaderRow"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../../util/shared";
    import ButtonNavigateToItem from "../../element/button/ButtonNavigateToItem";
    import ButtonDelete from "../../element/button/ButtonDelete";
    import ListHeader from "./ListHeader";
    import ButtonAdd from "../../element/button/ButtonAdd";
    import arrayUtil from "../../../util/arrayUtil";

    export default {
        name: "ItemDescription",

        components: {
            ButtonAdd,
            ListHeader,
            ButtonDelete,
            ButtonNavigateToItem
        },

        props: {
            item: Boolean
        },
        
        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode,
                lang: state => state.dictionary.lang
            }),
        },

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
                ]
            }
        },

        methods: {
            sortByWeight(array) {
                return arrayUtil.sortByWeight(array);
            },

            addHeaderRow() {
                this.newHeaderRowMessage = "";
                if (this.newLineIsEmpty(this.parameter, this.value)) {
                    this.newHeaderRowMessage = "Parameter and value fields shouldn't be empty"
                } else if (this.rowIsInList(this.parameter)) {
                    this.newHeaderRowMessage = "Parameter already exists"
                } else {
                    let newHeaderRow = {
                        id: "",
                        name: "",
                        localizedName: "",
                        status: "added",
                        parameter: this.parameter,
                        value: this.value,
                        itemId: "",
                        message: ""
                    };

                    this.itemView.header.rows.push(newHeaderRow);

                    this.parameter = "";
                    this.value = "";
                }
            },

            newLineIsEmpty(parameter, value) {
                return parameter === "" || value === "";
            },

            rowIsInList(parameter) {
                return shared.isInArray(parameter, this.itemView.header.rows);
            },

            isShowInfoButton(itemId) {
                return itemId !== "-" && !this.editMode;
            },

            isRemoveHeaderRowButtonVisible(deletable) {
                return this.editMode && deletable;
            },

            removeItem(row) {
                shared.removeFromArray(row, this.itemView.header.rows);
            },

            rowParamIsEditable(row) {
                if (!this.rowIsDeletable(row)) {
                    return false;
                }
                if (!this.isAdmin() && shared.isInArray(row.name, this.notEditableParams)) {
                    return false;
                }
                return this.isEdit();
            },

            rowIsDeletable(row) {
                if (shared.isInArray(row.name, this.notDeletableRows)) {
                    return false;
                }
                return this.isEdit();
            },

            isEdit() {
                return this.item && this.editMode;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            isAdmin() {
                return false;
                // return userUtil.isAdmin(this.itemView);
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