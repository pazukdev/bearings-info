<template>
    <div>
        <table class="equal-columns-table">
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
    import shared from "../../util/shared";
    import itemViewUtil from "../../util/itemViewUtil";
    import axios from "axios";

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

            addItemToWishList() {
                // user/{username}/add-item-to-wishlist/{item-id}
                let itemId = this.itemView.itemId;
                axios
                    .put(this.basicUrl
                            + "/" + "user"
                        + "/" + this.userName
                        + "/" + "add-item-to-wishlist"
                        + "/" + itemId, {
                            headers: {
                                Authorization: this.authorization
                            }
                        })
                    .then(response => {
                        let added = response.data === true;
                        if (added) {
                            this.itemView.wishListIds.push(itemId);
                        }
                    });
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

</style>