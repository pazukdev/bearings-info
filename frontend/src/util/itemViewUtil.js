import shared from "./shared";
import storeUtil from "./storeUtil";

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

        nestedTables.sort((a,b) => (a.name > b.name) ? 1 : -1);

        return nestedTables;
    },

    isGuest(itemView, userName) {
        return itemView.userData.comment === "Guest" && userName.toString() === "guest";
    },

    isAdmin(itemView) {
        return itemView.userData.comment === "Admin";
    },

    getItemName(itemView) {
        return itemView.header.rows[0].value;
    },

    getTitle(table) {
        return table.localizedName;
    },

    dispatchView(store, itemView) {
        store.dispatch("setItemView", itemView);
        storeUtil.setLoadingState(store, false);
    },

    removeItemFromItemList(itemView, item) {
        shared.removeFromArray(item, itemView.partsTable.parts);
        itemView.idsToRemove.push(item.itemId);
    },
}