<template>
    <div>
        <div v-if="editMode">
            <p>{{"Add part"}}</p>
            <p v-if="showMessage" class="alert-message">
                {{message}}
            </p>
            <table v-if="showAddForm">
                <tbody>
                <tr>
                    <td class="three-column-table-left-column">
                        <input v-model="newItem.location" type="text"/>
                    </td>
                    <td class="three-column-table-middle-column">
                        <ItemSelect :replacer="false"
                                    @hide-add-form="hideAddForm"
                                    @on-change="itemSelectOnChange"/>
                    </td>
                    <td class="three-column-table-right-column">
                        <input v-model="newItem.quantity" type="text"/>
                    </td>
                    <td class="three-column-table-button-column">
                        <button type="button"
                                class="round-button"
                                @click="addItem">
                            {{"+"}}
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import ItemSelect from "./ItemSelect";
    import {mapState} from "vuex";
    import shared from "../shared";

    export default {
        name: "AddPartForm",

        components: {
            ItemSelect
        },

        props: {
            editMode: Boolean,
            showForm: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            }),

            showMessage() {
                return !shared.messageIsEmpty(this.message);
            },

            showAddForm() {
                return this.editMode && this.showForm;
            }
        },

        data() {
            return {
                message: "",
                newItem: ""
            }
        },

        methods: {
            hideAddForm() {
                this.$emit("hide-add-form");
                this.message = "No parts found";
            },

            addItem() {
                this.message = "";
                this.newItem.name = this.parentItemName + this.newItem.name;
                if (this.newItem.quantity === "0") {
                    this.message = "Quantity shouldn't be zero";
                } else if (this.newItem.quantity === "-") {
                    this.message = "Quantity shouldn't contain -";
                }
                if (this.message !== "") {
                    return;
                }
                this.itemView.partsTable.parts.push(this.newItem);
            },

            itemSelectOnChange(selectedItem) {
                this.message = "";
                this.newItem = selectedItem;
            }

        }
    }
</script>

<style scoped>
    p {
        text-align: center;
        margin-top: 10px;
    }

    table, tr {
        width: 100%;
        padding: 0;
        margin: 0;
    }

    * {
        margin: 0;
        padding: 0;
    }
</style>