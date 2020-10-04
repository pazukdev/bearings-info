<template>
    <div class="default-margin" v-if="isOrdinaryItem(itemView) && itemView.category.toLowerCase() !== 'vehicle'">
        <details>
            <summary class="bold">{{getTitle()}}</summary>
            <div v-if="!editMode && sortedBuyLinks.length === 0"
                 class="default-margin" style="margin: 10px 0">
                {{translate("No stores added yet")}}
            </div>

            <table v-if="!editMode">
                <tbody>
                <tr v-for="link in sortedBuyLinks" v-if="sortedBuyLinks.length > 0">
                    <td><flag :iso="link.countryCode"/></td>
                    <td>
                        <div>
                            <a class="button"
                               :href="link.url" target="_blank">
                                {{link.translatedName}}
                            </a>
                        </div>
                    </td>
                    <td>
                        <img :src="getWebsiteFavicon(link.url)" alt="website favicon">
                    </td>
                    <td style="text-align: left">
                        {{getWebsiteAddress(link.url)}}
                    </td>
                </tr>
                <tr v-if="!editMode && sortedBuyLinks.length > 0"><td colspan="4"><hr/></td></tr>
                <tr>
                    <td/>
                    <td>
                        <div>
                            <a class="button" :href="getGoogleQueryUrl()" target="_blank">
                                {{translate("Google it")}}
                            </a>
                        </div>
                    </td>
                    <td>
                        <img :src="getWebsiteFavicon('https://www.google.com')" alt="website favicon">
                    </td>
                    <td style="text-align: left">
                        {{getWebsiteAddress('https://www.google.com')}}
                    </td>
                </tr>
                </tbody>
            </table>

            <DeletedItemsList v-if="getDeletedBuyLinks().length > 0"
                              :array="getDeletedBuyLinks()"
                              :buy-link="true"
                              @restore="restore"/>

            <table v-if="editMode">
                <tbody>
                <tr v-for="link in getBuyLinks()">
                    <td>
                        <table class="bordered">
                            <tr>
                                <td class="not-symmetrical-left" style="text-align: left"/>
                                <td style="text-align: right">
                                    <ButtonDelete :item="link" @remove-item="remove(link)"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="not-symmetrical-left">
                                    <select v-if="isEditable(link)" v-model="link.countryCode">
                                        <option v-for="country in getCountries(link.countryCode)"
                                                :value="country.alpha2Code">
                                            {{translate(country.name)}}
                                        </option>
                                    </select>
                                    <div v-else style="text-align: left">
                                        {{translate(getCountryName(link.countryCode))}}
                                    </div>
                                </td>
                                <td/>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input v-model="link.url"
                                           type="url"
                                           :placeholder="translate('Link')"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="bordered">
                            <tr>
                                <td class="not-symmetrical-left" style="text-align: left">
                                    {{translate("Add link")}}
                                </td>
                                <td style="text-align: right">
                                    <ButtonAdd v-if="!isEmpty(newBuyLink.url) && !isEmpty(newBuyLink.countryCode)"
                                               @add-item="add(newBuyLink)"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="not-symmetrical-left">
                                    <select v-model="newBuyLink.countryCode">
                                        <option v-if="isCountryDisplayed(country.alpha2Code)"
                                                v-for="country in countries"
                                                :value="country.alpha2Code">
                                            {{translate(country.name)}}
                                        </option>
                                    </select>
                                </td>
                                <td/>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input v-model="newBuyLink.url"
                                           type="url"
                                           :placeholder="translate('Add new link here')"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </details>
    </div>
</template>

<script>
import DeletedItemsList from "../element/DeletedItemsList";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";
import {mapState} from "vuex";
import shared from "../../util/shared";
import ButtonAdd from "../element/button/ButtonAdd";
import ButtonDelete from "../element/button/ButtonDelete";
import itemViewUtil from "../../util/itemViewUtil";

export default {
        name: "WhereToBuy",

        components: {ButtonDelete, ButtonAdd, DeletedItemsList},

        mixins: [basicComponent, view],

        computed: {
            ...mapState({
                countries: state => state.dictionary.countries
            }),

            sortedBuyLinks() {
                return this.getSortedBuyLinks();
            }
        },

        data() {
            return {
                deletedBuyLinks: [],
                newBuyLink: {id: "", url: "", countryCode: ""},
                autodocCountries: ["PL", "CZ", "LT", "LV", "EE", "FI", "SE", "GB", "FR", "ES", "IT",
                    "AT", "CH", "NL", "BE", "SK", "HU", "RO", "BG", "NO", "SI", "PT"]
            }
        },

        methods: {
            getGoogleQueryUrl() {
                // let textBefore = this.translate("buy") + " ";
                return itemViewUtil.getGoogleQueryUrl(this.itemView, "");
            },

            getWebsiteAddress(url) {
                return shared.getWebsiteAddress(url);
            },

            getWebsiteFavicon(url) {
                let googleUrl = "https://s2.googleusercontent.com/s2/favicons?domain=";
                return googleUrl + this.getWebsiteAddress(url);
            },

            getTitle() {
                // let inXCountries = this.translate("in countries");
                return this.translate("Where to buy")
                    + " "
                    + "("
                    // + inXCountries.split(" ")[0]
                    // + " "
                    + this.sortedBuyLinks.length
                    // + " "
                    // + inXCountries.split(" ")[1]
                    + ")"
            },

            getDeletedBuyLinks() {
                return this.deletedBuyLinks;
            },

            getBuyLinks() {
                return this.itemView.buyLinks;
            },

            getSortedBuyLinks() {
                let links = this.getBuyLinks().slice();
                let manufacturer = this.itemView.manufacturer;
                let itemName = this.itemView.name;

                this.addAutodocRuLink(links);

                let famousCompany = manufacturer === "Corteco"
                    || manufacturer === "Payen"
                    || manufacturer === "Victor Reinz"
                    || manufacturer === "Ajusa"
                    || manufacturer === "SWAG"
                    || manufacturer === "Bosch"
                    || manufacturer === "Jp Group";

                if (this.isCountryInArray("DE", links) || famousCompany) {
                    let deUrl;
                    if (famousCompany) {
                        deUrl = "https://www.autodoc.de/search?keyword=" + itemName;
                    } else {
                        for (let i = 0; i < links.length; i++) {
                            if (links[i].countryCode === "DE"
                                && (links[i].url.includes(".autodoc.de")
                                    || links[i].url.includes("123bearing.eu"))) {
                                deUrl = links[i].url;
                                if (links[i].url.includes("123bearing.eu")) {
                                    let bearing123Countries = ["US", "GB", "FR", "IT", "ES", "AT", "BE"];
                                    for (let i = 0; i < bearing123Countries.length; i++) {
                                        let countryCode = bearing123Countries[i];
                                        links.push({id: "", url: deUrl, countryCode: countryCode});
                                    }
                                    return this.sortBuyLinksByTranslatedCountryName(links);
                                }
                            }
                        }
                    }
                    if (!this.isEmpty(deUrl) || famousCompany) {
                        for (let i = 0; i < this.autodocCountries.length; i++) {
                            let countryCode = this.autodocCountries[i];
                            let basicUrl = "autodoc.";
                            if (countryCode === "GB" || countryCode === "NO") {
                                basicUrl = basicUrl + "co.";
                            } else if (countryCode === "FR"
                                || countryCode === "IT"
                                || countryCode === "AT"
                                || countryCode === "CH"
                                || countryCode === "PT") {
                                basicUrl = "auto-doc.";
                            } else if (countryCode === "RO") {
                                basicUrl = "autodoc24.";
                            }
                            let urlCountryCode = countryCode === "GB" ? "UK" : countryCode;
                            let replaceWith = basicUrl + urlCountryCode;
                            let url = deUrl.replace("autodoc.de", replaceWith);
                            links.push({id: "", url: url, countryCode: countryCode});
                        }
                    }
                }

                if (manufacturer === "USSR") {
                    for (let i = 0; i < links.length; i++) {
                        let link = links[i];
                        if (link.countryCode === "EE" && link.url === "http://auto-link") {
                            link.url = this.getEastHighWayLinkUrl(itemName);
                        }
                    }
                }

                return this.sortBuyLinksByTranslatedCountryName(links);
            },

            addAutodocRuLink(links) {
                if (this.isCountryInArray("BY", links) && !this.isCountryInArray("RU", links)) {
                    let autodocBy = ".autodoc.by";
                    for (let i = 0; i < links.length; i++) {
                        let linkUrl = links[i].url;
                        if (linkUrl.includes(autodocBy)) {
                            let url = linkUrl.replace(autodocBy, ".autodoc.ru");
                            links.push({id: "", url: url, countryCode: "RU"});
                        }
                    }
                }
            },

            sortBuyLinksByTranslatedCountryName(links) {
                // links = this.removeMarkedLinks(links);
                for (let j = 0; j < links.length; j++) {
                    let link = links[j];
                    link.translatedName = this.translate(this.getCountryName(link.countryCode));
                }
                return links.sort((a,b) => {
                    return b.translatedName < a.translatedName ? 1 : -1
                });
            },

            getEastHighWayLinkUrl(itemName) {
                let lang = "eng/home";
                if (this.lang.toString() === "ru") {
                    lang = "rus/glavnaja";
                } else if (this.lang.toString() === "de") {
                    lang = "ger/start";
                } else if (this.lang.toString() === "et") {
                    lang = "est/avaleht";
                } else if (this.lang.toString() === "fi") {
                    lang = "fin/kotisivu";
                }
                return "https://easthighway.com/"
                    + lang
                    + "/0/0/action/search/searchResults?search="
                    + itemName;
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
</style>