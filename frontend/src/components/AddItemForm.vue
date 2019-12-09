<template>
    <div>
        <table>
            <tbody>
            <tr>
                <td class="alert-message" colspan="5">
                    {{message}}
                </td>
            </tr>
            <tr v-if="notStub && isEditMode">
                <td>
                    <input v-model="newItem.comment" type="text"/>
                </td>
                <td>
                    <ItemSelect :parent-item-id="parentItemId"
                                :items="items"
                                :possible-items="possibleItems"
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
            isEditMode: Boolean,
            notStub: Boolean,
            replacer: Boolean,
            items: Array,
            possibleItems: Array
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
            addItem() {
                this.message = "";
                this.newItem.name = this.parentItemName + this.newItem.name;
                let e = this.replacer ? "add-replacer" : "add-part";
                this.$emit(e, this.newItem);
                this.newItem = {
                    comment: ""
                }
            },

            onChange(selectedItem) {
                this.message = "";
                this.newItem = selectedItem;
                this.$emit("replacer-select-on-change")
            }

        }
    }
</script>

<style scoped>

</style>