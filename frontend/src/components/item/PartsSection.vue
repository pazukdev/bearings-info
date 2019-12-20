<template>
    <div>
        <PartsTable :show-form="showForm"
                    @show-add-form="showAddForm"
                    @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import PartsTable from "./PartsTable";

    export default {
        name: "PartsSection",

        components: {
            PartsTable
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