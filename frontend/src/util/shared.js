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
        // console.log("get country name for: " + alpha2Code);
        let notFoundValue = "-";
        if (this.isEmpty(alpha2Code)) {
            return notFoundValue;
        }
        let countries = store.getters.countries;
        for (let i = 0; i < countries.length; i++) {
            if (countries[i].alpha2Code === alpha2Code) {
                // console.log(countries[i]);
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

    roughSizeOfObject(object) {
        return JSON.stringify(object).length / 1000000
        // let objectList = [];
        // let stack = [ object ];
        // let bytes = 0;
        //
        // while ( stack.length ) {
        //     let value = stack.pop();
        //     if (typeof value === 'boolean') {
        //         bytes += 4;
        //     }
        //     else if (typeof value === 'string') {
        //         bytes += value.length * 2;
        //     }
        //     else if (typeof value === 'number') {
        //         bytes += 8;
        //     }
        //     else if (typeof value === 'object' && objectList.indexOf(value) === -1) {
        //         objectList.push( value );
        //         for(let i in value) {
        //             stack.push(value[i]);
        //         }
        //     }
        // }
        // return bytes / 1000000;
    }

}