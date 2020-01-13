<template>
    <div style="text-align: center" v-if="!editMode">
        <br>
        <hr>
        <p>{{"Usage"}}</p>
<!--        {{itemView.parents.type}}<br>-->
<!--        {{itemView.parents.partsTable.parts}}<br>-->
        <p v-if="itemView.parents.partsTable.parts.length < 1">{{"Items not found"}}</p>
        <ItemList v-else :items-management-view="true" :usage-view="true" :item-view-prop="itemView.parents"/>
        <div style="height: 100px"/>
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
                itemView: state => state.dictionary.itemView,
                editMode: state => state.dictionary.editMode
            })
        },
    }
</script>

<style scoped>

</style>