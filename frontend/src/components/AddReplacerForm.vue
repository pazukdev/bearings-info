<template>
    <div id="add-item-form">
        <p>{{title}}</p>
        <p v-if="message !== ''" class="alert-message">
            {{message}}
        </p>
        <table v-if="showForm">
            <tbody v-if="replacer">
            <tr v-if="editMode">
                <td class="not-symmetrical-left">
                    <ItemSelect :replacer="replacer"
                                @show-add-form="showAddForm"
                                @on-change="selectOnChange"/>
                </td>
                <td class="not-symmetrical-right"/>
                <td>
                    <button type="button"
                            class="round-button"
                            @click="addItem">
                        {{"+"}}
                    </button>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea v-model="newItem.comment"/>
                </td>
            </tr>
            </tbody>

            <tbody v-if="!replacer">
            <tr v-if="editMode">
                <td class="three-column-table-left-column">
                    <input v-model="newItem.location" type="text"/>
                </td>
                <td class="three-column-table-middle-column">
                    <ItemSelect :replacer="replacer"
                                @show-add-form="showAddForm"
                                @on-change="selectOnChange"/>
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
        name: "AddReplacerForm",

        components: {
            ItemSelect
        },

        props: {
            editMode: Boolean,
            replacer: Boolean,
            showForm: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            }),

            possibleItems() {
                if (this.replacer) {
                    return this.itemView.possibleReplacers;
                } else {
                    return this.itemView.possibleParts;
                }
            },

            items() {
                if (this.replacer) {
                    return this.itemView.replacersTable.replacers;
                } else {
                    return this.itemView.partsTable.parts;
                }
            },

            title() {
                return this.replacer ? "Add replacer" : "Add part";
            }
        },

        data() {
            return {
                message: "",
                newItem: {
                    comment: ""
                }
            }
        },

        methods: {
            showAddForm(show) {
                this.message = "";
                if (!show) {
                    this.message = this.replacer ? "No replacers found" : "No parts found";
                }
                this.$parent.showAddForm(show);
            },

            addItem() {
                this.message = "";
                this.newItem.name = this.parentItemName + this.newItem.name;
                if (!this.replacer) {
                    if (this.newItem.quantity === "0") {
                        this.message = "Quantity shouldn't be zero";
                    } else if (this.newItem.quantity === "-") {
                        this.message = "Quantity shouldn't contain -";
                    }
                }
                if (this.message !== "") {
                    return;
                }
                this.items.push(this.newItem);
                this.newItem = {
                    comment: ""
                }
            },

            selectOnChange(selectedItem) {
                this.message = "";
                this.newItem = selectedItem;
                this.$emit("select-on-change")
            }

        }
    }
</script>

<style scoped>
    .three-column-table-left-column, .three-column-table-middle-column {
        width: 46%;
    }

    #add-item-form {
    }

    p {
        text-align: center;
        margin-top: 10px;
    }
</style>