<template>
    <div style="text-align: center">
        <img class="preview" alt="App image"
             src="https://pazukdev.github.io/sovietboxers/img/app_logo.9a3c3892.png">
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