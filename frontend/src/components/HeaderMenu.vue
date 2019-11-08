<template>
    <div>
        <table id="header-menu">
            <tbody>
            <tr>
                <td class="third-part-wide">
                    <button type="button"
                            v-if="isShowWishlistButton()"
                            v-on:click="openWishlist()">
                        {{"Wishlist: " + itemsCountInWishlist + " items"}}
                    </button>
                </td>
                <td></td>
                <td class="third-part-wide" style="text-align: right">
                    <div v-if="!guest">{{userData.itemName}}</div>
                    <div v-if="!guest">{{"Rating: " + userData.rating}}</div>
                    <div v-if="admin">{{"You are admin"}}</div>
                    <div v-if="guest">{{"You are guest"}}</div>
                </td>
            </tr>
            <tr><td colspan="3"><hr></td></tr>
            <tr>
                <td>
                    <button v-if="addToWishlistButtonVisible"
                            type="button"
                            @click="addItemToWishlist()">
                        {{"Add to Wish List"}}
                    </button>
                    <p v-if="itemInWishlistTextVisible">
                        {{"Item in Wish List"}}
                    </p>
                </td>
                <td></td>
                <td>
                    <button v-if="searchEnabled"
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
    export default {
        name: "TableView.vue",

        props: {
            userData: Object,
            guest: Boolean,
            admin: Boolean,
            wishListView: Boolean,
            addToWishlistButtonVisible: Boolean,
            itemInWishlistTextVisible: Boolean,
            searchEnabled: Boolean,
            showBottomHr: Boolean,
            itemsCountInWishlist: Number,
            itemNameForSearchInGoogle: String
        },

        methods: {
            searchInGoogle() {
                let q = "buy " + this.itemNameForSearchInGoogle;
                window.open('http://google.com/search?q=' + q);
            },

            openWishlist() {
                this.$emit('open-wish-list');
            },

            addItemToWishlist() {
                this.$emit('add-item-to-wishlist');
            },

            isShowWishlistButton() {
                return !this.wishListView && !this.guest;
            }
        }
    }
</script>

<style scoped>
    #header-menu {
        border-spacing: 0;
    }
</style>