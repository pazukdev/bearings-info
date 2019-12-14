<template>
    <div>
        <ReplacersTable :edit-mode="editMode" @show-add-form="showAddForm"/>
        <AddReplacerForm :edit-mode="editMode" :show-form="showForm" @hide-add-form="hideAddForm"/>
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

        props: {
            editMode: Boolean
        },

        data() {
            return {
                showForm: true
            }
        },

        methods: {
            // updateReplacers(rateReplacer) {
            //     this.$emit("update-replacers", rateReplacer);
            // },

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