<template>
    <div>
        <button type="button" @click="onClick">
            {{text}}
        </button>
    </div>
</template>

<script>
    export default {
        name: "DefaultButton",

        props: {
            text: String
        },

        methods: {
            onClick() {
                this.$emit("on-click");
            }
        }
    }
</script>

<style scoped>

</style>