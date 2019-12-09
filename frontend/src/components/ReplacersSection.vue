<template>
    <div>
        <div>{{showForm}}</div>
        <ReplacersTable v-if="replacersTableVisible"
                        :replacers-table="replacersTable"
                        :edit-mode="editMode"
                        :not-stub="notStub"
                        :rated-items="ratedItems"
                        @navigate-to-item="navigateToItem"/>

        <AddItemForm :parent-item-id="parentItemId"
                     :parent-item-name="parentItemName"
                     :edit-mode="editMode"
                     :not-stub="notStub"
                     :replacer="true"
                     :items="replacersTable.replacers"
                     :possible-items="possibleReplacers"
                     :show-form="showForm"
                     @replacer-select-on-change="replacerSelectOnChange"
                     @add-replacer="addReplacer"/>
    </div>
</template>

<script>
    import ReplacersTable from "./ReplacersTable";
    import AddItemForm from "./AddItemForm";

    export default {
        name: "ReplacersSection",

        components: {
            ReplacersTable,
            AddItemForm
        },

        props: {
            replacersTableVisible: Boolean,
            replacersTable: Object,
            ratedItems: Array,
            parentItemId: Number,
            parentItemName: String,
            editMode: Boolean,
            notStub: Boolean,
            possibleReplacers: Array
        },

        data() {
            return {
                showForm: true
            }
        },

        methods: {
            showAddForm(show) {
                this.showForm = show === true;
            },

            navigateToItem(itemId) {
                this.$emit("navigate-to-item", itemId);
            },

            isInArray(element, array) {
                return this.$parent.isInArray(element, array);
            },

            isGuest() {
                return this.$parent.isGuest();
            },

            replacerSelectOnChange() {
                this.$emit("replacer-select-on-change");
            },

            addReplacer(event, newReplacer) {
                this.$emit(event, newReplacer);
            },

            removeReplacerFromList(replacer) {
                this.$parent.removeFromArray(replacer, this.replacersTable.replacers);
                this.showForm = true;
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
            }
        }
    }
</script>

<style scoped>

</style>