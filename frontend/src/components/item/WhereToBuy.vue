<template>
    <div class="default-margin">
        <details>
            <summary class="bold">{{getTitle()}}</summary>
            <div v-if="!editMode && sortedBuyLinks.length === 0"
                 class="default-margin" style="margin: 10px 0">
                {{translate("No stores added yet")}}
            </div>

            <table v-if="!editMode && sortedBuyLinks.length > 0">
                <tbody>
                <tr v-for="link in sortedBuyLinks" style="text-align: left">
                    <td>
                        <div class="country-seller">
                            <flag :iso="link.countryCode"/>
                            {{" "}}
                            <a class="simple-link country-seller"
                               :href="link.url" target="_blank">
                                {{link.translatedName}}
                            </a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

            <DeletedItemsList v-if="getDeletedBuyLinks().length > 0"
                              :array="getDeletedBuyLinks()"
                              :buy-link="true"
                              @restore="restore"/>

            <table v-if="editMode && (isAdmin() || isEditor())">
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

                if (this.isCountryInArray("BY", links)) {
                    let autodocBy = ".autodoc.by";
                    for (let i = 0; i < links.length; i++) {
                        let linkUrl = links[i].url;
                        if (linkUrl.includes(autodocBy)) {
                            let url = linkUrl.replace(autodocBy, ".autodoc.ru");
                            links.push({id: "", url: url, countryCode: "RU"});
                        }
                    }
                }

                let famousCompany = this.itemView.manufacturer === "Corteco"
                    || this.itemView.manufacturer === "Payen"
                    || this.itemView.manufacturer === "Victor Reinz"
                    || this.itemView.manufacturer === "Ajusa"
                    || this.itemView.manufacturer === "SWAG"
                    || this.itemView.manufacturer === "Jp Group";

                if (this.isCountryInArray("DE", links) || famousCompany) {
                    let deUrl;
                    if (famousCompany) {
                        deUrl = "https://www.autodoc.de/search?keyword=" + this.itemView.name;
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
                return this.sortBuyLinksByTranslatedCountryName(links);
            },

            sortBuyLinksByTranslatedCountryName(links) {
                for (let j = 0; j < links.length; j++) {
                    let link = links[j];
                    link.translatedName = this.translate(this.getCountryName(link.countryCode));
                }
                return links.sort((a,b) => {
                    return b.translatedName < a.translatedName ? 1 : -1
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