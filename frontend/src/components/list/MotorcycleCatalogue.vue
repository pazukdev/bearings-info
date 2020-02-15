<template>
    <div style="text-align: center">
        <img class="preview" alt="App image"
             src="https://pazukdev.github.io/sovietboxers/img/old-vehicles_280x280.5b585050.png">
        <ItemList :vehicles="true"/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";

    export default {
        name: "MotorcycleCatalogue",
        
        components: {ItemList}
    }
</script>

<style scoped>
    img {
        padding: 20px 0;
    }
</style>