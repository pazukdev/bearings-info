import shared from "./shared";
import storeUtil from "./storeUtil";
import arrayUtil from "./arrayUtil";

export default {
    itemsListToTables(items) {
        let categories = [];
        for (let i = 0; i < items.length; i++) {
            let category = items[i].itemCategory;
            if (!shared.isInArray(category, categories)) {
                categories.push(category);
            }
        }

        let nestedTables = [];
        for (let i = 0; i < categories.length; i++) {
            let category = categories[i];

            let nestedTable = {
                name: category,
                items: [],
                opened: true
            };

            for (let i = 0; i < items.length; i++) {
                let item = items[i];
                if (item.itemCategory === category) {
                    nestedTable.items.push(item);
                }
            }

            nestedTables.push(nestedTable);
        }

        return arrayUtil.sortByName(nestedTables);
    },

    setLocale(router, route, i18n, lang) {
        if (route.params.lang !== lang) {
            this.changeLanguageInUrl(router, lang);
        }
        i18n.locale = lang;
    },

    changeLanguageInUrl(router, lang) {
        router.replace({path: router.currentRoute.path.replace(/\/[^/]*$/, "/" + lang)});
    },

    isAuthorized(authorization) {
        return authorization !== "";
    },

    isGuest(userName) {
        return userName.toString() === "guest";
    },

    isAdmin(itemView) {
        return itemView.userData.role === "ADMIN";
    },

    isSeller(itemView) {
        return itemView.userData.role === "SELLER";
    },

    getItemName(itemView) {
        return itemView.header.rows[0].value;
    },

    dispatchView(store, itemView) {
        store.dispatch("setItemView", itemView);
        storeUtil.setLoadingState(false);
    },

    removeItemFromItemList(itemView, item) {
        shared.removeFromArray(item, itemView.partsTable.parts);
        itemView.idsToRemove.push(item.itemId);
    },

    isMotorcycleCatalogueView(itemView) {
        return itemView.itemId === -2;
    },

    isManufacturer(itemView) {
        // console.log(itemView.category.toLowerCase());
        return itemView.category.toLowerCase() === "manufacturer"
    }
}