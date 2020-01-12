<template>
    <div v-if="!isLoginPage()">
<!--        {{itemView.userData}}-->
        <table>
            <tbody>
                <tr>
                    <td class="third-part-wide">
                        <DefaultButton v-if="!isGuest()" @on-click="openWishlist" :text="getWishListButtonText()"/>
                    </td>
                    <td/>
                    <td class="third-part-wide" style="text-align: right">
                        <div v-if="!isGuest()">{{itemView.userData.name}}</div>
                        <div v-if="!isGuest()">{{$t("rating") + ": " + itemView.userData.rating}}</div>
                        <div v-if="isAdmin()">{{$t("youAreAdmin")}}</div>
                        <div v-if="isGuest()">{{$t('youAreGuest')}}</div>
                    </td>
                </tr>
            </tbody>
        </table>
        <hr>
        <div v-if="true && isAdmin()">
            <details open>
                <summary>{{"Information for admin"}}</summary>
                <table class="equal-columns-table" style="text-align: left">
                    <tbody>
                        <tr>
                            <td>{{"Business logic time"}}</td>
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
                <hr>
            </details>
        </div>
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
            openWishlist() {
                routerUtil.toWishlist();
            },

            getWishListButtonText() {
                return this.$t("wishlist") + ": " + this.getItemsCount() + " " + this.$t("itemsPcs");
            },

            getItemsCount() {
                return this.itemView.wishListIds.length;
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

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
    summary {
        text-align: center;
    }
</style>