<template>
    <div class="default-margin bordered" style="text-align: center; border-color: #6ab04c"
         v-if="isAdminMessageRendered()">
        <p>
            <b>{{translate(getMessage())}}</b>
        </p>
        <a class="simple-link" v-if="!isEmpty(itemView.adminMessage) && !isEmpty(itemView.adminMessage.url)"
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
            isAdminMessageRendered() {
                return !this.isEmpty(this.getMessage());
            },

            getMessage() {
                if (!this.isEmpty(this.$route.params.message)) {
                    return this.$route.params.message;
                }
                if (!this.isEmpty(this.itemView.adminMessage)) {
                    return this.itemView.adminMessage.text;
                }
                return "";
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