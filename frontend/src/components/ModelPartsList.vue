<template>
    <div>
        <ModelBearingList :engine="engine"/>
        <ModelSealList :motorcycle="motorcycle"/>
    </div>
</template>

<script>
    import ModelBearingList from "./ModelBearingList";
    import ModelSealList from "./ModelSealList";

    export default {
        name: "ModelPartsList.vue",

        props: ['motorcycle', 'engine'],

        components: {
            ModelBearingList,
            ModelSealList
        }
    }

</script>
<style scoped>

</style>