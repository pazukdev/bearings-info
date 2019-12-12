<template>
    <div>
        <PartsTable :edit-mode="editMode"/>

        <AddItemForm v-if="editMode"
                     :edit-mode="editMode"
                     :show-form="showForm"
                     :replacer="false"/>
    </div>
</template>

<script>
    import AddItemForm from "./AddItemForm";
    import PartsTable from "./PartsTable";

    export default {
        name: "ReplacersSection",

        components: {
            PartsTable,
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
            updateReplacers(rateReplacer) {
                this.$emit("update-replacers", rateReplacer);
            },

            showAddForm(show) {
                this.showForm = show === true;
            },

            navigateToItem(itemId) {
                this.$emit("navigate-to-item", itemId);
            },

            isInArray(element, array) {
                return this.$parent.isInArray(element, array);
            },

            arrayHaveActiveItems(array) {
                return this.$parent.arrayHaveActiveItems(array);
            },

            isGuest() {
                return this.$parent.isGuest();
            },

            removeReplacerFromList(replacer) {
                this.$parent.removeFromArray(replacer, this.replacersTable.replacers);
                this.showForm = true;
            },

            statusIsActive(status) {
                return this.$parent.statusIsActive(status);
            },

            removeItem(item, array) {
                this.$parent.removeFromArray(item, array);
                this.showForm = true;
            }
        }
    }
</script>

<style scoped>

</style>