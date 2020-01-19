<template>
    <div v-if="!isLoginPage()">
        <div v-if="!isEmpty(errorMessage)" class="alert-message" style="text-align: center">
            {{"Error!"}}<br>
            {{errorMessage}}
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import shared from "../../util/shared";
    import routerUtil from "../../util/routerUtil";

    export default {
        name: "MessagesSection",

        computed: {
            ...mapState({
                errorMessage: state => state.dictionary.errorMessage
            })
        },

        methods: {
            isEmpty(errorMessage) {
                return shared.isEmpty(errorMessage);
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
            }
        }
    }
</script>

<style scoped>

</style>