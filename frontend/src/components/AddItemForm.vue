<template>
    <div>
        <p v-if="!showForm"
           style="text-align: center; margin-top: 16px">
            {{getMessage()}}
        </p>
        <table v-if="showForm">
            <tbody>
            <tr v-if="notStub && editMode">
                <td>
                    <input v-model="newItem.comment" type="text"/>
                </td>
                <td>
                    <ItemSelect :parent-item-id="parentItemId"
                                :items="items"
                                :possible-items="possibleItems"
                                @show-add-form="showAddForm"
                                @on-change="onChange"/>
                </td>
                <td></td>
                <td></td>
                <td>
                    <button type="button"
                            class="round-button"
                            @click="addItem">
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

</style>