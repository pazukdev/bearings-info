<template>
    <div>
        <ItemList :motorcycle-catalogue-view="true"/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";

    export default {
        name: "MotorcycleCatalogue",
        
        components: {
            ItemList
        }
    }
</script>

<style scoped>

</style>