<template>
    <div>
        <button type="button"
                @click="pushTo(part.itemId)">
            {{part.buttonText}}
        </button>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "ButtonNavigateToItem",

        props: {
            part: Object
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