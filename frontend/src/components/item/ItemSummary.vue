<template>
    <div>
        <CountedItemList :summary-view="true" :sorted="true"/>
    </div>
</template>

<script>
    import CountedItemList from "../list/CountedItemList";

    export default {
        name: "ItemSummary",
        components: {CountedItemList}
    }
</script>

<style scoped>
</style>