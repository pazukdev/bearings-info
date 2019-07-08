<template>
    <div>
        <ModelBearingList :motorcycleId="motorcycleId"/>
        <ModelSealList :motorcycleId="motorcycleId"/>
    </div>
</template>

<script>
    import ModelBearingList from "./ModelBearingList";
    import ModelSealList from "./ModelSealList";

    export default {
        name: "ModelPartsList.vue",

        props: ['motorcycleId'],

        components: {
            ModelBearingList,
            ModelSealList
        }
    }

</script>
<style scoped>

</style>