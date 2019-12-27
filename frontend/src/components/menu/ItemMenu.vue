<template>
    <div>
<!--        {{"wikiLink: " + itemView.wikiLink}}<br>-->
<!--        {{"sellerLink: " + itemView.sellerLink}}<br>-->
<!--        {{"sellerLang: " + itemView.sellerLang}}<br>-->
<!--        {{"validationMessage: " + validationMessage}}-->
        <table class="equal-columns-table">
            <tbody v-if="!editMode">
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
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <button v-if="isLinkButtonRendered(itemView.sellerLink, itemView.sellerLang)"
                                type="button"
                                @click="open(itemView.sellerLink)">
                            {{"Seller"}}
                        </button>
                    </td>
                    <td>
                        <button v-if="isLinkButtonRendered(itemView.wikiLink, 'all')"
                                type="button"
                                @click="open(itemView.wikiLink)">
                            {{"Wiki"}}
                        </button>
                    </td>
                    <td>
                        <button v-if="isSearchEnabled()"
                                type="button"
                                @click="searchInGoogle()">
                            {{"Google"}}
                        </button>
                    </td>
                </tr>
            </tbody>
            <tbody v-if="editMode" style="text-align: left">
                <tr style="text-align: center"><td>{{"Links"}}</td></tr>
                <tr style="text-align: center">
                    <td><p class="alert-message">{{validationMessage}}</p></td>
                </tr>
                <tr>
                    <td>
                        <label>{{"Wiki"}}
                            <input id="wiki-link-input" v-model="itemView.wikiLink"
                                   type="url"
                                   @change="validate()"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{"Seller"}}
                            <input id="seller-link-input" v-model="itemView.sellerLink"
                                   :disabled="!isInputEnabled()"
                                   type="url"
                                   @change="validate()"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{"Seller language"}}
                            <select v-model="itemView.sellerLang">
                                <option v-for="lang in langs" :key="lang">
                                    {{lang}}
                                </option>
                            </select>
                        </label>
                    </td>
                </tr>
            </tbody>
        </table>
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
                appLanguage: state => state.dictionary.appLanguage,
                langs: state => state.dictionary.langs
            })
        },

        data() {
            return {
                validationMessage: ""
            }
        },

        methods: {
            validate() {
                this.validationMessage = document.getElementById("wiki-link-input").validationMessage;
                if (shared.isEmpty(this.validationMessage)) {
                    this.validationMessage = document.getElementById("seller-link-input").validationMessage;
                }
            },

            open(link) {
                window.open(link);
            },

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
            },

            isLinkButtonRendered(link, lang) {
                if (lang == null || lang === "all" || lang === this.appLanguage) {
                    return !this.isEmpty(link);
                }
                return false;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            isInputEnabled() {
                if (shared.isInArray(this.userName, this.getPermittedToEditSellerLinkUserNameList())) {
                    return true;
                }
                return this.isAdmin();
            },

            getPermittedToEditSellerLinkUserNameList() {
                return ["serg"];
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            }
        }
    }
</script>

<style scoped>
    select {
        width: initial;
        height: initial;
    }
</style>