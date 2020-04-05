<template>
    <div>
        <button type="button"
                class="round-button green"
                :title="translate('Add')"
                @click="addItem()">
            {{"+"}}
        </button>
    </div>
</template>

<script>
    import basicComponent from "../../../mixin/basicComponent";

    export default {
        name: "ButtonAdd",

        mixins: [basicComponent],

        methods: {
            addItem() {
                this.$emit("add-item");
            }
        }
    }
</script>

<style scoped>

</style>