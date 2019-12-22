<template>
    <div>
        <table>
            <tbody>
                <tr>
                    <td class="third-part-wide">
                        <DefaultButton v-if="!isGuest()" @on-click="openWishlist" :text="getWishListButtonText()"/>
                    </td>
                    <td/>
                    <td class="third-part-wide" style="text-align: right">
                        <div v-if="!isGuest()">{{itemView.userData.itemName}}</div>
                        <div v-if="!isGuest()">{{$t("rating") + ": " + itemView.userData.rating}}</div>
                        <div v-if="isAdmin()">{{$t("youAreAdmin")}}</div>
                        <div v-if="isGuest()">{{$t('youAreGuest')}}</div>
                    </td>
                </tr>
            </tbody>
        </table>
        <hr>
        <hr>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import itemViewUtil from "../../util/itemViewUtil";
    import DefaultButton from "../element/button/DefaultButton";

    export default {
        name: "UserMenu",
        components: {DefaultButton},
        computed: {
            ...mapState({
                userName: state => state.dictionary.userName,
                itemView: state => state.dictionary.itemView,
            })
        },

        methods: {
            openWishlist() {
                this.$router.push({name: "wish_list"});
            },

            getWishListButtonText() {
                return this.$t("wishlist") + ": " + this.getItemsCount() + " " + this.$t("itemsPcs");
            },

            getItemsCount() {
                return this.itemView.wishListIds.length;
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName.toString());
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            }
        }
    }
</script>

<style scoped>

</style>