<template>
    <div id="vehicles-list" style="text-align: center">
        <img class="preview" alt="App image" :src="getImg()">
        <br><br>
        <p style="font-weight: bold">
            {{translate("Boxer owners of the world, unite!")}}
        </p>
        <br><br>
        <ItemList :vehicles="true"/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";
    import basicComponent from "../../mixin/basicComponent";

    export default {
        name: "MotorcycleCatalogue",
        components: {ItemList},
        mixins: [basicComponent],
        methods: {
            getImg() {
                return "https://pazukdev.github.io/sovietboxers/img/old-vehicles_280x280.5b585050.png";
            }
        }
    }
</script>

<style scoped>
    img {
        padding: 20px 0;
    }
</style>