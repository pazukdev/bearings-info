<template>
    <div>
<!--        <DefaultButton @on-click="pushTo(part.itemId)" :text="getText(part)"/>-->
        <router-link v-if="user" class="button"
                     :to="{name: 'user', params: {id: part.itemId, lang: lang}}">
            {{part.buttonText}}
        </router-link>
        <router-link v-else class="button" :to="{name: 'item', params: {id: part.itemId, lang: lang}}">
<!--            {{translate(part.buttonText.split("=")[0].trim())}}<br>-->
<!--            {{translate(part.buttonText.split("=")[1])}}-->
            {{part.buttonText.split("=")[0].trim()}}<br>
            {{part.buttonText.split("=")[1]}}
        </router-link>
    </div>
</template>

<script>
    import DefaultButton from "./DefaultButton";
    import routerUtil from "../../../util/routerUtil";
    import {mapState} from "vuex";
    import dictionaryUtil from "../../../util/dictionaryUtil";

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
                lang: state => state.dictionary.lang
            })
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
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>

</style>