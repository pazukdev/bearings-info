<template>
    <div>
        {{editMode}}
        <ReplacersTable v-if="replacersTableVisible"
                        :replacers-table="replacersTable"
                        :edit-mode="editMode"
                        :not-stub="notStub"
                        :rated-items="ratedItems"
                        @update-replacers="updateReplacers"
                        @navigate-to-item="navigateToItem"/>

        <AddItemForm v-if="editMode"
                     :edit-mode="editMode"
                     :show-form="showForm"
                     :replacer="true"
                     @select-on-change="selectOnChange"/>
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

            isGuest() {
                return this.$parent.isGuest();
            },

            selectOnChange() {
                this.$emit("select-on-change");
            },

            addReplacer(event, newReplacer) {
                this.$emit(event, newReplacer);
            },

            removeItem(item) {
                let array = this.replacersTable.replacers;
                this.$parent.removeFromArray(item, array);
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