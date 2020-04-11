<template>
    <div>
        <button type="button"
                class="round-button green"
                :title="translate('Add')"
                @click="$emit('add-item')">
            {{"+"}}
        </button>
    </div>
</template>

<script>
    import basicComponent from "../../../mixin/basicComponent";

    export default {
        name: "ButtonAdd",

        mixins: [basicComponent]
    }
</script>

<style scoped>

</style>