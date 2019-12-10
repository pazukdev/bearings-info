<template>
    <div id="add-item-form">
        <p>{{getTitle()}}</p>
        <p v-if="!showForm">
            {{getMessage()}}
        </p>
        <table v-if="showForm" class="bordered">
            <tbody>
            <tr v-if="notStub && editMode">
                <td class="not-symmetrical-left">
                    <ItemSelect :parent-item-id="parentItemId"
                                :items="items"
                                :possible-items="possibleItems"
                                @show-add-form="showAddForm"
                                @on-change="onChange"/>
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
        </table>
    </div>
</template>

<script>
    import ItemSelect from "./ItemSelect";

    export default {
        name: "AddReplacerForm",

        components: {
            ItemSelect
        },

        props: {
            parentItemId: Number,
            parentItemName: String,
            editMode: Boolean,
            notStub: Boolean,
            replacer: Boolean,
            items: Array,
            possibleItems: Array,
            showForm: Boolean
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
                this.$parent.showAddForm(show);
            },

            getTitle() {
                return this.replacer ? "Add replacer" : "Add part";
            },

            getMessage() {
                return this.replacer ? "No replacers found" : "No parts found";
            },

            addItem() {
                this.message = "";
                this.newItem.name = this.parentItemName + this.newItem.name;
                let e = this.replacer ? "add-replacer" : "add-part";
                this.$emit(e, e, this.newItem);
                this.newItem = {
                    comment: ""
                }
            },

            onChange(selectedItem) {
                this.message = "";
                this.newItem = selectedItem;
                this.$emit("replacer-select-on-change")
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
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