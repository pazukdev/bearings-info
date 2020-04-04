<template>
    <div>
        <button type="button" @click="onClick" :class="myClass">
            {{text.split("=")[0]}}<br>{{text.split("=")[1]}}
        </button>
    </div>
</template>

<script>
    export default {
        name: "DefaultButton",

        props: {
            text: String,
            myClass: String
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