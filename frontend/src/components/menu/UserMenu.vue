<template>
    <div v-if="!isLoginPage()">
        <details class="default-margin">
            <summary>{{translate("Performance report")}}</summary>
            <table class="equal-columns-table" style="text-align: left">
                <tbody>
                    <tr>
                        <td>{{translate("Getting data time")}}</td>
                        <td>{{getTime(itemView.businessLogicTime)}}</td>
                    </tr>
                    <tr>
                        <td>{{translate("Translation time")}}</td>
                        <td>{{getTime(itemView.translationTime)}}</td>
                    </tr>
                    <tr>
                        <td>{{translate("Response total time")}}</td>
                        <td>{{getTime(itemView.responseTotalTime)}}</td>
                    </tr>
                </tbody>
            </table>
        </details>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import DefaultButton from "../element/button/DefaultButton";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";
    import dictionaryUtil from "../../util/dictionaryUtil";
    import userUtil from "../../util/userUtil";

    export default {
        name: "UserMenu",

        components: {DefaultButton},

        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState
            })
        },

        methods: {
            isAdmin() {
                return userUtil.isAdmin(this.itemView);
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
            },

            getTime(value) {
                if (this.loadingState || shared.isEmpty(value)) {
                    return "-";
                }
                return value.toFixed(2) + " " + this.translate("sec");
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            }
        }
    }
</script>

<style scoped>
</style>