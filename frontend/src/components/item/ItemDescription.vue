<template>
    <div>
        <div style="text-align: center">
            <p><b>{{itemView.header.name}}</b></p>
            <p>{{$t("createdBy") + " " + itemView.creatorName}}</p>
            <br>
        </div>

        <table id="item-description">
            <tbody>
            <tr style="text-align: left"
                v-for="row in itemView.header.rows">
                <td class="two-columns-table-left-column">
                    <p>
                        {{row.parameter}}
                    </p>
                </td>
                <td class="two-column-table-right-column">
                    <input v-if="editMode" v-model="row.value" type="text"/>
                    <p v-if="!isShowInfoButton(row.itemId) && !editMode">
                        {{row.value}}
                    </p>
                    <ButtonNavigateToItem v-if="isShowInfoButton(row.itemId)"
                                          :part="row"
                                          :info-button="true"/>
                </td>
                <td>
                    <ButtonDelete :item="row" @remove-item="removeItem"/>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="alert-message">
                    {{newHeaderRowMessage}}
                </td>
            </tr>
            <tr v-if="editMode">
                <td>
                    <input v-model="parameter" type="text"/>
                </td>
                <td>
                    <input v-model="value" type="text"/>
                </td>
                <td>
                    <button type="button"
                            class="round-button"
                            @click="addHeaderRow()">
                        {{"+"}}
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <hr>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import ButtonNavigateToItem from "../element/button/ButtonNavigateToItem";
    import ButtonDelete from "../element/button/ButtonDelete";

    export default {
        name: "ItemDescription",

        components: {
            ButtonDelete,
            ButtonNavigateToItem
        },
        
        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            }),
        },

        data() {
            return {
                newHeaderRowMessage: "",
                parameter: "",
                value: ""
            }
        },

        methods: {
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
        }
    }
</script>

<style scoped>

</style>