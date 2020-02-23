<template>
    <div class="default-margin" style="text-align: center"
         v-if="isAdminMessageRendered(itemView.adminMessage)">
        <p v-if="!isEmpty(itemView.adminMessage.text)">
            <b>{{translate(itemView.adminMessage.text)}}</b>
        </p>
        <a class="simple-link" v-if="!isEmpty(itemView.adminMessage.url)"
           :href="itemView.adminMessage.url">{{getLinkText(itemView.adminMessage)}}</a>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";

    export default {
        name: "AdminMessage",

        computed: {
            ...mapState({
                itemView: state => state.dictionary.itemView
            })
        },

        methods: {
            isAdminMessageRendered(message) {
                return !this.isEmpty(message) && !this.isEmpty(message.text);
            },

            getLinkText(source) {
                return !this.isEmpty(source.linkText) ? this.translate(source.linkText) : source.url;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },
        }
    }
</script>

<style scoped>

</style>