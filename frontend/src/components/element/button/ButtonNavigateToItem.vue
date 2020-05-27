<template>
    <div>
        <router-link v-if="user" class="button"
                     :to="{name: 'user', params: {id: part.itemId, lang: lang}}">
            {{part.buttonText}}
        </router-link>
        <router-link v-else
                     class="button"
                     :to="{name: 'item', params: {id: part.itemId, lang: lang}}">
            {{part.buttonText.split("=")[0].trim()}}<br>
            {{part.buttonText.split("=")[1]}}
        </router-link>
    </div>
</template>

<script>
    import DefaultButton from "./DefaultButton";
    import basicComponent from "../../../mixin/basicComponent";

    export default {
        name: "ButtonNavigateToItem",

        components: {DefaultButton},

        props: {
            part: Object,
            infoButton: Boolean,
            user: Boolean
        },

        mixins: [basicComponent]
    }
</script>

<style scoped>

</style>