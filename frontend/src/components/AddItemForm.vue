<template>
    <div>
        <p>{{"Add part"}}</p>
        <p v-if="message !== ''" class="alert-message">
            {{message}}
        </p>
        <table v-if="showForm">
            <tbody>
            <tr v-if="editMode">
                <td class="three-column-table-left-column">
                    <input v-model="newItem.location" type="text"/>
                </td>
                <td class="three-column-table-middle-column">
                    <ItemSelect :replacer="false"
                                @show-add-form="showAddForm"
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
</template>

<script>
    import ItemSelect from "./ItemSelect";
    import {mapState} from "vuex";

    export default {
        name: "AddItemForm",

        components: {
            ItemSelect
        },

        props: {
            editMode: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        data() {
            return {
                message: String,
                showForm: true,
                newItem: Object
            }
        },

        methods: {
            showAddForm(show) {
                this.message = "";
                if (!show) {
                    this.message = "No parts found";
                }
                this.showForm = show === true;
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
            },

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