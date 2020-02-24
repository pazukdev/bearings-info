<template>
    <div v-if="!isLoginPage()">
        <details class="default-margin">
            <summary>{{"Performance report"}}</summary>
            <table class="equal-columns-table" style="text-align: left">
                <tbody>
                    <tr>
                        <td>{{"Logic time"}}</td>
                        <td>{{getTime(itemView.businessLogicTime)}}</td>
                    </tr>
                    <tr>
                        <td>{{"Translation time"}}</td>
                        <td>{{getTime(itemView.translationTime)}}</td>
                    </tr>
                    <tr>
                        <td>{{"Response total time"}}</td>
                        <td>{{getTime(itemView.responseTotalTime)}}</td>
                    </tr>
                </tbody>
            </table>
        </details>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import DefaultButton from "../element/button/DefaultButton";
    import routerUtil from "../../util/routerUtil";
    import shared from "../../util/shared";

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
                return itemViewUtil.isAdmin(this.itemView);
            },

            isLoginPage() {
                return routerUtil.isLogin(this.$route);
            },

            getTime(value) {
                if (this.loadingState || shared.isEmpty(value)) {
                    return "-";
                }
                return value.toFixed(2) + " sec";
            }
        }
    }
</script>

<style scoped>
</style>