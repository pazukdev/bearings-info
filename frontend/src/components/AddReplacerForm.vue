<template>
    <div>
        <div v-if="editMode">
            <p>{{"Add replacer"}}</p>
            <p v-if="showMessage" class="alert-message">
                {{message}}
            </p>
            <table v-if="showAddForm" class="bordered">
                <tbody>
                <tr>
                    <td class="not-symmetrical-left">
                        <ItemSelect :replacer="true"
                                    @hide-add-form="hideAddForm"
                                    @on-change="itemSelectOnChange"/>
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
                        <textarea v-model="itemComment"/>
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
    import itemViewUtil from "../itemViewUtil";

    export default {
        name: "AddReplacerForm",

        components: {
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
                this.item.name = itemViewUtil.getItemName(this.itemView) + this.item.name;
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