<template>
    <div>
        <table class="equal-columns-table">
            <tbody v-if="!editMode">
                <tr>
                    <td>
                        <DefaultButton id="user-hard-delete"
                                       v-if="isAddToWishListButtonRendered()"
                                       :text="translate('Add to Wishlist')"
                                       :my-class="'background-blue'"
                                       @on-click="addItemToWishList()"/>
                        <p v-if="isItemInWishListTextVisible()" class="green">
                            {{translate("Item is in Wishlist")}}
                        </p>
                    </td>
                    <td/>
                    <td/>
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
                <tr>
                    <td colspan="3"/>
                </tr>
                <tr>
                    <td>
                        <div v-if="!isEmpty(itemView.manualLink)">
                            <a :href="itemView.manualLink" target="_blank">
                                <img style="height: 60px" src="../../assets/manual_100x100.png">
                            </a>
                            <br>
                            {{translate("Manual")}}
                        </div>
                    </td>
                    <td>
                        <div v-if="!isEmpty(itemView.partsCatalogLink)">
                            <a :href="itemView.partsCatalogLink" target="_blank">
                                <img style="height: 60px" src="../../assets/parts_catalog_100x100.png">
                            </a>
                            <br>
                            {{translate("Parts catalog")}}
                        </div>
                    </td>
                    <td>
                        <div v-if="!isEmpty(itemView.drawingsLink)">
                            <a :href="itemView.drawingsLink" target="_blank">
                                <img style="height: 60px" src="../../assets/drawings_100x100.png">
                            </a>
                            <br>
                            {{translate("Drawings")}}
                        </div>
                    </td>
                </tr>
            </tbody>
            <tbody v-if="editMode" style="text-align: left">
                <tr style="text-align: center"><td>{{translate("Links")}}</td></tr>
                <tr style="text-align: center">
                    <td><p class="alert-message">{{validationMessage}}</p></td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Wiki")}}
                            <input v-model="itemView.wikiLink" type="url" :placeholder="translate('Link')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Store / seller / official website")}}
                            <input v-model="itemView.websiteLink" type="url" :placeholder="translate('Link')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Seller language")}}
                            <select id="website-lang-select" v-model="itemView.websiteLang">
                                <option v-for="lang in langs" :key="lang">
                                    {{lang}}
                                </option>
                            </select>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Manual")}}
                            <input v-model="itemView.manualLink" type="url" :placeholder="translate('Link')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Parts catalog")}}
                            <input v-model="itemView.partsCatalogLink" type="url" :placeholder="translate('Link')"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>{{translate("Drawings")}}
                            <input v-model="itemView.drawingsLink" type="url" :placeholder="translate('Link')"/>
                        </label>
                    </td>
                </tr>
            </tbody>
        </table>

        <table v-if="editMode && isAdmin()">
            <tbody>
                <tr><td colspan="2"><hr></td></tr>
                <tr><td colspan="2"><b>{{"Admin section"}}</b></td></tr>
                <tr>
                    <td style="text-align: left">
                        {{"Items status"}}
                    </td>
                    <td>
                        <select v-model="itemView.status">
                            <option v-for="status in ['active', 'deleted']" :value="status">
                                {{status}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr><td colspan="2"><hr></td></tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import shared from "../../util/shared";
    import axios from "axios";
    import userUtil from "../../util/userUtil";
    import routerUtil from "../../util/routerUtil";
    import DefaultButton from "../element/button/DefaultButton";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "ItemMenu",

        components: {DefaultButton},

        mixins: [basicComponent, view],

        computed: {
            buttonStyles() {
                return {
                    background: this.isManufacturer() ? '' : '#6ab04c',
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
                let itemName = itemView.name;
                let textBefore;
                let category = itemView.category.toLowerCase();
                if (category === "manufacturer" || category === "standard") {
                    textBefore = "";
                } else {
                    textBefore = this.translate("buy") + " ";
                }
                if (category === "vehicle" && !this.isEmpty(itemView.vehicleClass)) {
                    category = itemView.vehicleClass;
                }
                let q = textBefore + this.translate(category.toLowerCase()) + " " + this.translate(itemName);
                window.open('http://google.com/search?q=' + q);
            },

            addItemToWishList() {
                let itemId = this.itemView.itemId;
                axios
                    .put(this.basicUrl
                            + "/" + "user"
                        + "/" + userUtil.getUserName()
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

            isSearchEnabled() {
                return this.itemView.searchEnabled;
            },

            isWebsiteLinkButtonRendered(link, lang) {
                if (lang == null || lang === "all" || lang === routerUtil.getLang(this.$route)) {
                    return !this.isEmpty(link);
                }
                return false;
            },

            getButtonText() {
                if (this.isManufacturer()) {
                    return this.translate("Website");
                } else {
                    return this.translate("Seller");
                }
            }
        }
    }
</script>

<style scoped>
    #website-lang-select {
        width: initial;
        height: initial;
    }
</style>