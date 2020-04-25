<template>
    <div>
        <ItemList :usage-view="true" :item-view-prop="itemView.parents"/>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ItemList from "../list/ItemList";

    export default {
        name: "Usage",

        components: {ItemList},

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },
    }
</script>

<style scoped>
</style>