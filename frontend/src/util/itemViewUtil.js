import shared from "./shared";
import storeUtil from "./storeUtil";
import arrayUtil from "./arrayUtil";
import searchUtil from "./searchUtil";
import routerUtil from "./routerUtil";
import dictionaryUtil from "./dictionaryUtil";

export default {

    translate(text) {
        return dictionaryUtil.translate(text);
    },

    translateItem(item) {
        // item.manufacturer = dictionaryUtil.translate(item.manufacturer);
        // item.itemCategory = dictionaryUtil.translate(item.itemCategory);
        item.vehicleClass = dictionaryUtil.translate(item.vehicleClass);
    },

    itemsListToTables(items, sort, filter, opened) {
        if (!shared.isEmpty(filter) && filter.length > 4) {
            opened = true;
        }

        let categories = [];
        for (let i = 0; i < items.length; i++) {
            let category = items[i].itemCategory;
            if (!shared.isInArray(category, categories)) {
                categories.push(category);
            }
        }

        let itemsCount = 0;
        let nestedTables = [];
        for (let i = 0; i < categories.length; i++) {
            let category = categories[i];

            let nestedTable = {
                name: !shared.isEmpty(category) ? category : this.translate("Not specified"),
                items: [],
                opened: opened
            };

            for (let i = 0; i < items.length; i++) {
                let item = items[i];
                if (item.itemCategory === category && searchUtil.filterItem(item, filter)) {
                    this.translateItem(item);
                    nestedTable.items.push(item);
                    itemsCount++;
                }
            }

            if (sort) {
                nestedTable.items = arrayUtil.sortByComment(nestedTable.items);
            }

            if (nestedTable.items.length > 0) {
                nestedTables.push(nestedTable);
            }
        }

        return {
            tables: arrayUtil.sortByName(nestedTables),
            itemsCount: itemsCount
        };
    },

    isAuthorized(authorization) {
        return authorization !== "";
    },

    getItemName(itemView) {
        return itemView.header.rows[0].value;
    },

    dispatchView(view, lang) {
        view.deletedChildren = [];
        view.deletedReplacers = [];
        storeUtil.setErrorMessage(view.errorMessage);
        storeUtil.setView(view);
        // storeUtil.setUserData(view.userData);
        storeUtil.setLoadingStateOff();
        if (storeUtil.userIsBlocked()) {
            console.log("Open Login page. Reason: blocked user is logged in");
            routerUtil.toLogin(lang);
        }
        // routerUtil.selectLanguage(routerUtil.getLang());
    },

    dispatchResponseError(error) {
        console.error(error);
        let message = "No server response";
        storeUtil.setErrorMessage(message);
    },

    isMotorcycleCatalogueView(itemView) {
        return itemView.itemId === -2;
    },

    isManufacturer(itemView) {
        return itemView.category.toLowerCase() === "manufacturer"
    },

    getGoogleQueryUrl(itemView, textBefore) {
        let itemName = itemView.name;
        let category = itemView.category.toLowerCase();
        if (category === "vehicle" && !shared.isEmpty(itemView.vehicleClass)) {
            category = itemView.vehicleClass;
        }
        let textAfter = " " + this.translate(category.toLowerCase());
        let q = textBefore + this.translate(itemName) + textAfter;
        return 'http://google.com/search?q=' + q;
    }

}