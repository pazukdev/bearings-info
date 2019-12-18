<template>
    <div>
        <button type="button"
                @click="pushTo(part.itemId)">
            {{getText(part)}}
        </button>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "ButtonNavigateToItem",

        props: {
            part: Object,
            infoButton: Boolean,
            user: Boolean
        },

        computed: {
            ...mapState({
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            pushTo(id) {
                let lang = this.appLanguage.toString();
                console.log(this.user + " " + id + " " + lang);
                if (this.user) {
                    this.$router.push({ name: "user", params: {id, lang} });
                } else {
                    this.$router.push({ name: "item", params: {id, lang} });
                }
            },

            getText(item) {
                if (this.infoButton) {
                    return item.value;
                } else {
                    return item.buttonText;
                }
            }
        }
    }
</script>

<style scoped>

</style>