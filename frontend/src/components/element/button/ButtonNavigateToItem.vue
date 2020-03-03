<template>
    <div>
        <DefaultButton @on-click="pushTo(part.itemId)" :text="getText(part)"/>
    </div>
</template>

<script>
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

        methods: {
            pushTo(id) {
                let lang = this.$route.params.lang;
                if (this.user) {
                    routerUtil.toUser(id, lang);
                } else {
                    routerUtil.toItem(id, lang);
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