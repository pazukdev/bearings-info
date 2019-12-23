<template>
    <div>
        <DefaultButton @on-click="pushTo(part.itemId)" :text="getText(part)"/>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import DefaultButton from "./DefaultButton";
    import routerUtil from "../../../util/routerUtil";

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
                    routerUtil.toItem(this.$router, id, lang);
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