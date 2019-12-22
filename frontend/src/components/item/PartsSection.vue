<template>
    <div>
        <PartList :show-form="showForm"
                  @show-add-form="showAddForm"
                  @hide-add-form="hideAddForm"/>
    </div>
</template>

<script>
    import PartList from "../list/PartList";

    export default {
        name: "PartsSection",

        components: {PartList},

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