<template>
    <div>
        <ModelBearingList :motorcycle="motorcycle"/>
        <ModelSealList :motorcycle="motorcycle"/>
    </div>
</template>

<script>
    import ModelBearingList from "./ModelBearingList";
    import ModelSealList from "./ModelSealList";

    export default {
        name: "ModelPartsList.vue",

        props: ['motorcycle'],

        components: {
            ModelBearingList,
            ModelSealList
        }
    }

</script>
<style scoped>

</style>