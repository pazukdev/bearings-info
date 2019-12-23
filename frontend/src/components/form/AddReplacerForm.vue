<template>
    <div v-if="editMode">
        <p>{{"Add replacer"}}</p>
        <p v-if="showMessage" class="alert-message">
            {{message}}
        </p>
        <table v-if="showAddForm">
            <tbody>
            <tr>
                <td>
                    <table class="bordered">
                        <tbody>
                        <tr>
                            <td class="not-symmetrical-left">
                                <ItemSelect :replacer="true"
                                            @hide-add-form="hideAddForm"
                                            @on-change="itemSelectOnChange"/>
                            </td>
                            <td class="not-symmetrical-right"/>
                            <td>
                                <ButtonAdd @add-item="addItem"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <textarea v-model="itemComment"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import ItemSelect from "../element/ItemSelect";
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import ButtonAdd from "../element/button/ButtonAdd";

    export default {
        name: "AddReplacerForm",

        components: {
            ButtonAdd,
            ItemSelect
        },

        props: {
            showForm: Boolean
        },

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            }),

            message() {
                return this.showForm ? "" : "No possible replacers found";
            },

            showMessage() {
                return !shared.messageIsEmpty(this.message);
            },

            showAddForm() {
                return this.editMode && this.showForm;
            }
        },

        data() {
            return {
                item: "",
                itemComment: ""
            }
        },

        methods: {
            hideAddForm() {
                this.$emit("hide-add-form");
            },

            addItem() {
                // this.item.name = itemViewUtil.getItemName(this.itemView) + this.item.name;
                this.item.comment = this.itemComment;

                let newItem = this.item;
                this.itemView.replacersTable.replacers.push(newItem);

                this.item = "";
                this.itemComment = "";
            },

            itemSelectOnChange(selectedItem) {
                this.item = selectedItem;
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