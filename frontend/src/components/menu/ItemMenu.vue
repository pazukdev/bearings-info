<template>
    <div>
<!--        {{"wikiLink: " + itemView.wikiLink}}<br>-->
<!--        {{"websiteLink: " + itemView.websiteLink}}<br>-->
<!--        {{"websiteLang: " + itemView.websiteLang}}<br>-->
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
                        <button id="website-link-button"
                                v-if="isWebsiteLinkButtonRendered(itemView.websiteLink, itemView.websiteLang)"
                                type="button"
                                :style="buttonStyles"
                                @click="open(itemView.websiteLink)">
                            {{getButtonText()}}
                        </button>
                    </td>
                    <td>
                        <button v-if="isWebsiteLinkButtonRendered(itemView.wikiLink, 'all')"
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
                        <label>{{"Wiki link"}}
                            <input id="wiki-link-input" v-model="itemView.wikiLink"
                                   type="url"
                                   @change="validate()"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{"Buy / website link"}}
                            <input id="seller-link-input" v-model="itemView.websiteLink"
                                   type="url"
                                   @change="validate()"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{"Website link language"}}
                            <select v-model="itemView.websiteLang">
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
    import userUtil from "../../util/userUtil";

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
            }),

            buttonStyles() {
                return {
                    backgroundColor: this.isManufacturer() ? '' : 'darkgreen',
                }
            }
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
                let itemView = this.itemView;
                let itemName = itemView.header.name.toLowerCase();
                let textBefore;
                let category = itemView.category.toLowerCase();
                if (category === "manufacturer" || category === "standard") {
                    textBefore = "";
                } else {
                    textBefore = this.$t("buy") + " ";
                }
                let q = textBefore + itemName;
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
                    && !this.isManufacturer()
                    && !this.isGuest();
            },

            isItemInWishListTextVisible() {
                return this.isInWishList(this.itemView.itemId) && !this.isGuest();
            },

            isInWishList(itemId) {
                return shared.isInArray(itemId, this.itemView.wishListIds);
            },

            isGuest() {
                return itemViewUtil.isGuest(this.userName);
            },

            isSearchEnabled() {
                return this.itemView.searchEnabled;
            },

            isWebsiteLinkButtonRendered(link, lang) {
                if (lang == null || lang === "all" || lang === this.appLanguage) {
                    return !this.isEmpty(link);
                }
                return false;
            },

            isEmpty(value) {
                return shared.isEmpty(value);
            },

            isInputEnabled() {
                return this.isAdmin() || userUtil.isCurrentUserItemCreator(this.userName, this.itemView.creatorName);
            },

            isAdmin() {
                return itemViewUtil.isAdmin(this.itemView);
            },

            getButtonText() {
                if (this.isManufacturer()) {
                    return "Website";
                } else {
                    return "Buy";
                }
            },

            isManufacturer() {
                return itemViewUtil.isManufacturer(this.itemView);
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