<template>
    <div id="vehicles-list" style="text-align: center">
        <img id="app-img" class="preview" alt="App image" :src="getImg()">
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
    import view from "../../mixin/view";
    import shared from "../../util/shared";

    export default {
        name: "MotorcycleCatalogue",
        components: {ItemList},
        mixins: [basicComponent, view],
        methods: {
            getImg() {
                if (!shared.isEmpty(this.itemView.cachedImg)) {
                    return this.itemView.cachedImg;
                }
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