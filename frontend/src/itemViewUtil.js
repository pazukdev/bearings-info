import shared from "./shared";

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
                items: []
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

    getItemName(itemView) {
        return itemView.header.rows[0].value;
    },

    getTitle(table) {
        return table.localizedName;
    }
}