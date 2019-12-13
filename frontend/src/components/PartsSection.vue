<template>
    <div>
        <PartsTable :edit-mode="editMode"/>
    </div>
</template>

<script>
    import PartsTable from "./PartsTable";

    export default {
        name: "ReplacersSection",

        components: {
            PartsTable
        },

        props: {
            editMode: Boolean
        }
    }
</script>

<style scoped>

</style>