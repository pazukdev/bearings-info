import router from "../plugins/router";
import store from "../plugins/store";

export default {
    isInArray(element, array) {
        for (let i=0; i < array.length; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    },

    arrayContainsSubstring(substring, array) {
        for (let i=0; i < array.length; i++) {
            if (array[i].includes(substring)) {
                return true;
            }
        }
        return false;
    },

    isEmpty(value) {
        return value === "-" || value === "" || value === null || value === undefined;
    },

    messageIsEmpty(message) {
        return this.isEmpty(message);
    },

    statusIsActive(status) {
        return status === "active";
    },

    removeFromArray(element, array) {
        array.splice(array.indexOf(element), 1);
    },

    isNestedItemsTitleVisible(nestedItemsList, editMode) {
        return nestedItemsList.length > 0 || editMode;
    },

    isLoading(loadingState) {
        return !this.isEmpty(loadingState);
    },

    getCurrentLocation() {
        return window.location;
    },

    isEnglish() {
        let lang = router.currentRoute.params.lang;
        if (this.isEmpty(lang)) {
            return false;
        }
        return lang === "en";
    },

    getCountryName(alpha2Code) {
        let notFoundValue = "-";
        if (this.isEmpty(alpha2Code)) {
            return notFoundValue;
        }
        let countries = store.getters.countries;
        for (let i = 0; i < countries.length; i++) {
            if (countries[i].alpha2Code === alpha2Code) {
                let name = countries[i].name;
                if (name.includes("United Kingdom")) {
                    return "UK";
                } else if (name.includes("Russian")) {
                    return "Russia";
                } else if (name.includes("United States")) {
                    return "USA";
                }
                return name;
            }
        }
        return notFoundValue;
    },

    getWebsiteAddress(url) {
        if (url.includes("alitems.com")) {
            url = "https://aliexpress.com";
        } else if (url.includes("ad.admitad.com")) {
            if (url.includes("ebay.com")) {
                url = "https://www.ebay.com";
            }
            if (url.includes("dok.ua") || url.includes("dok.dbroker.com.ua")) {
                url = "https://dok.ua";
            }
        }
        return (new URL(url)).hostname.replace("www.", "");
    }

}