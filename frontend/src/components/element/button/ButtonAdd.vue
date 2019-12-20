<template>
    <div>
        <button type="button"
                class="round-button"
                @click="addItem()">
            {{"+"}}
        </button>
    </div>
</template>

<script>
    export default {
        name: "ButtonAdd",

        methods: {
            addItem() {
                this.$emit("add-item");
            }
        }
    }
</script>

<style scoped>

</style>