<template>
    <div>
        <ReplacersTable @show-add-form="showAddForm"/>
        <AddReplacerForm :show-form="showForm" @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import ReplacersTable from "./ReplacersTable";
    import AddReplacerForm from "./AddReplacerForm";

    export default {
        name: "ReplacersSection",

        components: {
            ReplacersTable,
            AddReplacerForm
        },

        data() {
            return {
                showForm: true
            }
        },

        methods: {
            showAddForm() {
                this.showForm = true;
            },

            hideAddForm() {
                this.showForm = false;
            }
        }
    }
</script>

<style scoped>
</style>