export default {
    isInArray(element, array) {
        for (let i=0; i < array.length; i++) {
            if (array[i] === element) {
                return true;
            }
        }
        return false;
    },

    statusIsActive(status) {
        return status === "active";
    },

    removeFromArray(element, array) {
        array.splice(array.indexOf(element), 1);
    },



    itemsListToTables(items) {
        let categories = [];
        for (let i = 0; i < items.length; i++) {
            let category = items[i].itemCategory;
            if (!this.isInArray(category, categories)) {
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

        return nestedTables;
    }
}