<template>
    <div>
        <ReplacerList @show-add-form="showAddForm"/>
        <AddReplacerForm :show-form="showForm" @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import ReplacerList from "../list/ReplacerList";
    import AddReplacerForm from "../form/AddReplacerForm";

    export default {
        name: "ReplacersSection",

        components: {
            ReplacerList,
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