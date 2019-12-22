<template>
    <div>
        <DefaultButton @on-click="pushTo(part.itemId)" :text="getText(part)"/>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import DefaultButton from "./DefaultButton";

    export default {
        name: "ButtonNavigateToItem",
        components: {DefaultButton},
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
                    return item.localizedButtonText;
                }
            }
        }
    }
</script>

<style scoped>

</style>