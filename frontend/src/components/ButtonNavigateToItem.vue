<template>
    <div>
        <button v-if="!infoButton"
                type="button"
                @click="pushTo(part.itemId)">
            {{part.buttonText}}
        </button>
        <button v-if="infoButton"
                type="button"
                @click="pushTo(part.itemId)">
            {{part.value}}
        </button>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "ButtonNavigateToItem",

        props: {
            part: Object,
            infoButton: Boolean
        },

        computed: {
            ...mapState({
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            pushTo(itemId) {
                this.$router.push({ path: `/item/id/${itemId}/${this.appLanguage}` });
            }
        }
    }
</script>

<style scoped>

</style>