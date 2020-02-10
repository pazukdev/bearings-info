import shared from "./shared";
import storeUtil from "./storeUtil";
import arrayUtil from "./arrayUtil";
import searchUtil from "./searchUtil";
import store from "../plugins/store";
import routerUtil from "./routerUtil";

export default {
    itemsListToTables(items, sort, filter, opened) {
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
                name: !shared.isEmpty(category) ? category : "Not specified",
                items: [],
                opened: opened
            };

            for (let i = 0; i < items.length; i++) {
                let item = items[i];
                if (item.itemCategory === category && searchUtil.filterItem(item, filter)) {
                    nestedTable.items.push(item);
                }
            }

            if (sort) {
                nestedTable.items = arrayUtil.sortByComment(nestedTable.items);
            }

            if (nestedTable.items.length > 0) {
                nestedTables.push(nestedTable);
            }
        }

        return arrayUtil.sortByName(nestedTables);
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

    dispatchView(itemView) {
        storeUtil.setErrorMessage(itemView.errorMessage);
        store.dispatch("setItemView", itemView);
        storeUtil.setUserData(itemView.userData);
        storeUtil.setLoadingStateOff();
        if (storeUtil.userIsBlocked()) {
            console.log("Open Login page. Reason: blocked user is logged in");
            routerUtil.toLogin();
        }
    },

    dispatchResponseError(error) {
        console.error(error);
        storeUtil.setErrorMessage("Page error. Please, return to previous page");
    },

    removeItemFromItemList(itemView, item) {
        shared.removeFromArray(item, itemView.children);
        itemView.idsToRemove.push(item.itemId);
    },

    isMotorcycleCatalogueView(itemView) {
        return itemView.itemId === -2;
    },

    isManufacturer(itemView) {
        return itemView.category.toLowerCase() === "manufacturer"
    }
}