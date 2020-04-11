<template>
    <div>
<!--        {{itemView.buyLinks}}-->
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
                                v-if="!isEmpty(itemView.websiteLink)"
                                type="button"
                                :style="buttonStyles"
                                @click="open(itemView.websiteLink)">
                            {{getButtonText()}}
                        </button>
                    </td>
                    <td>
                        <button v-if="!isEmpty(itemView.wikiLink)"
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
                <tr v-if="itemView.buyLinks.length > 0">
                    <td colspan="3">
                        <details open>
                            <summary>{{translate("Where to buy")}}</summary>
                            <table>
                                <tbody>
                                    <tr v-for="link in sortedBuyLinks()" style="text-align: left">
                                        <td>
                                            <div class="country-seller">
                                                <flag :iso="link.countryCode"/>
<!--                                                <country-flag class="country-seller"-->
<!--                                                              :country="link.countryCode"/>-->
                                                {{" "}}
                                                <a class="simple-link country-seller"
                                                   :href="link.url" target="_blank">
                                                    {{translate(getCountryName(link.countryCode))}}
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </details>
                    </td>
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
                        <label>{{translate("Official website")}}
                            <input v-model="itemView.websiteLink" type="url" :placeholder="translate('Link')"/>
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

        <DeletedItemsList v-if="getDeletedBuyLinks().length > 0"
                          :array="getDeletedBuyLinks()"
                          :buy-link="true"
                          @restore="restore"/>

        <details open v-if="editMode && (isAdmin() || isEditor())" class="default-margin">
            <summary>{{translate("Where to buy")}}</summary>
            <table>
                <tbody>
                    <tr v-for="link in getBuyLinks()" style="text-align: left">
                        <td style="width: 70%">
                            <input v-model="link.url"
                                   type="url"
                                   :placeholder="translate('Link')"/>
                        </td>
                        <td>
                            <div>
                                <select v-model="link.countryCode">
                                    <option v-for="country in getCountries(link.countryCode)"
                                            :value="country.alpha2Code">
                                        {{translate(country.name)}}
                                    </option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <ButtonDelete :force-render="true" @remove-item="remove(link)"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input v-model="newBuyLink.url"
                                   type="url"
                                   :placeholder="translate('Link')"/>
                        </td>
                        <td>
                            <select v-model="newBuyLink.countryCode">
                                <option v-if="isCountryDisplayed(country.alpha2Code)"
                                        v-for="country in countries"
                                        :value="country.alpha2Code">
                                    {{translate(country.name)}}
                                </option>
                            </select>
                        </td>
                        <td>
                            <ButtonAdd @add-item="add(newBuyLink)"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </details>

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
    import DefaultButton from "../element/button/DefaultButton";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";
    import ButtonDelete from "../element/button/ButtonDelete";
    import DeletedItemsList from "../element/DeletedItemsList";
    import ButtonAdd from "../element/button/ButtonAdd";
    import {mapState} from "vuex";

    export default {
        name: "ItemMenu",

        components: {ButtonAdd, DeletedItemsList, ButtonDelete, DefaultButton},

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                countries: state => state.dictionary.countries
            }),
            buttonStyles() {
                return {
                    background: this.isManufacturer() ? '' : '#6ab04c',
                }
            }
        },

        data() {
            return {
                validationMessage: "",
                deletedBuyLinks: [],
                newBuyLink: {id: "", url: "", countryCode: ""}
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

            getDeletedBuyLinks() {
                return this.deletedBuyLinks;
            },

            getBuyLinks() {
                return this.itemView.buyLinks;
            },

            sortedBuyLinks() {
                return this.getBuyLinks().slice().sort((a,b) => {
                    return b.countryCode < a.countryCode ? 1 : -1
                });
            },

            add(buyLink) {
                this.getBuyLinks().push({
                    id: buyLink.id,
                    url: buyLink.url,
                    countryCode: buyLink.countryCode
                });

                buyLink.id = "";
                buyLink.url = "";
                buyLink.countryCode = "";
            },

            remove(buyLink) {
                shared.removeFromArray(buyLink, this.getBuyLinks());
                this.getDeletedBuyLinks().push(buyLink);
            },

            restore(buyLink) {
                shared.removeFromArray(buyLink, this.getDeletedBuyLinks());
                this.getBuyLinks().push(buyLink);
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

            getButtonText() {
                if (this.isManufacturer()) {
                    return this.translate("Website");
                } else {
                    return this.translate("Seller");
                }
            },

            getCountryName(alpha2Code) {
                return shared.getCountryName(alpha2Code);
            },

            isCountryDisplayed(alpha2Code) {
                return !(this.isCountryInArray(alpha2Code, this.getBuyLinks())
                    || this.isCountryInArray(alpha2Code, this.getDeletedBuyLinks()));
            },

            isCountryInArray(alpha2Code, array) {
                for (let i=0; i < array.length; i++) {
                    if (array[i].countryCode === alpha2Code) {
                        return true;
                    }
                }
                return false;
            },

            getCountries(currentCountryCode) {
                let countries = [];
                for (let i = 0; i < this.countries.length; i++) {
                    let country = this.countries[i];
                    if (this.isCountryDisplayed(country.alpha2Code)) {
                        countries.push(country);
                    }
                    if (country.alpha2Code === currentCountryCode) {
                        countries.push(country);
                    }
                }
                return countries;
            }
        }
    }
</script>

<style scoped>
    .country-seller {
        display: inline-block;
        vertical-align: middle;
    }

    #website-lang-select {
        width: initial;
        height: initial;
    }
</style>