<template>
    <div>
        <table id="header-menu">
            <tbody>
            <tr>
                <td class="third-part-wide">
                    <button type="button"
                            v-if="!isGuest()"
                            @click="openWishlist()">
                        {{getWishListButtonText()}}
                    </button>
                </td>
                <td></td>
                <td class="third-part-wide" style="text-align: right">
                    <div v-if="!isGuest()">{{itemView.userData.itemName}}</div>
                    <div v-if="!isGuest()">{{$t("rating") + ": " + itemView.userData.rating}}</div>
                    <div v-if="isAdmin()">{{$t("youAreAdmin")}}</div>
                    <div v-if="isGuest()">{{$t('youAreGuest')}}</div>
                </td>
            </tr>
            <tr><td colspan="3"><hr></td></tr>
            <tr>
                <td>
                    <button v-if="isAddToWishListButtonRendered()"
                            type="button"
                            @click="addItemToWishList()">
                        {{$t("addToWishList")}}
                    </button>
                    <p v-if="isItemInWishListTextVisible()">
                        {{$t("itemInWishList")}}
                    </p>
                </td>
                <td></td>
                <td>
                    <button v-if="isSearchEnabled()"
                            type="button"
                            @click="searchInGoogle()">
                        {{"Google"}}
                    </button>
                </td>
            </tr>
            <tr v-if="showBottomHr">
                <td colspan="3"><hr></td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import itemViewUtil from "../itemViewUtil";
    import shared from "../shared";
    import storeUtil from "../storeUtil";

    export default {
        name: "HeaderMenu.vue", // rename & divide into 2 components

        props: {
            itemInWishlistTextVisible: Boolean,
            searchEnabled: Boolean,
            showBottomHr: Boolean,
            itemsCountInWishlist: Number
        },

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                loadingState: state => state.dictionary.loadingState,
                itemView: state => state.dictionary.itemView,
                itemsManagementId: state => state.dictionary.itemsManagementId,
                motorcycleCatalogueId: state => state.dictionary.motorcycleCatalogueId,
                wishlistId: state => state.dictionary.wishlistId,
                userlistId: state => state.dictionary.userlistId,
                appLanguage: state => state.dictionary.appLanguage
            })
        },

        methods: {
            searchInGoogle() {
                let itemName = this.itemView.header.name.toLowerCase();
                let q = this.$t("buy") + " " + itemName;
                window.open('http://google.com/search?q=' + q);
            },

            openWishlist() {
                this.$router.push({name: "wish_list"});
            },

            getWishListButtonText() {
                return this.$t("wishlist") + ": " + this.getItemsCount() + " " + this.$t("itemsPcs");
            },

            addItemToWishList() { // make it easier
                storeUtil.setLoadingState(this.$store, true);
                this.itemView.addToWishList = true;
                this.$emit("save");
            },

            isAddToWishListButtonRendered() {
                return !this.isInWishList(this.itemView.itemId)
                    && !this.editMode
                    && !this.isGuest();
            },

            isItemInWishListTextVisible() {
                return this.isInWishList(this.itemView.itemId) && !this.isGuest();
            },

            isInWishList(itemId) {
                return shared.isInArray(itemId, this.itemView.wishListIds);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.itemView, this.userName.toString());
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            getItemsCount() {
                return this.itemView.wishListIds.length;
            },

            isSearchEnabled() {
                return this.itemView.searchEnabled;
            }
        }
    }
</script>

<style scoped>
    #header-menu {
        border-spacing: 0;
    }
</style>