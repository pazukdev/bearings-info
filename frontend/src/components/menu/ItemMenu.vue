<template>
    <div>
        <table>
            <tbody>
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
            </tbody>
        </table>
        <hr>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import storeUtil from "../../util/storeUtil";
    import shared from "../../util/shared";
    import itemViewUtil from "../../util/itemViewUtil";

    export default {
        name: "ItemMenu",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                editMode: state => state.dictionary.editMode,
                itemView: state => state.dictionary.itemView,
            })
        },

        methods: {
            searchInGoogle() {
                let itemName = this.itemView.header.name.toLowerCase();
                let q = this.$t("buy") + " " + itemName;
                window.open('http://google.com/search?q=' + q);
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