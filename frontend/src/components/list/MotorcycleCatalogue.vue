<template>
    <div>
        <Header :item="false"/>
        <ItemList/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";
    import Header from "./section/Header";

    export default {
        name: "MotorcycleCatalogue",
        
        components: {
            Header,
            ItemList
        }
    }
</script>

<style scoped>

</style>