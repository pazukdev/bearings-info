import shared from "./shared";
import dictionaryUtil from "./dictionaryUtil";

export default {
    
    translate(text) {
        return dictionaryUtil.translate(text);
    },
    
    createSelectText(item) {
        let category = item.itemCategory;
        let addManufacturer = this.isAddManufacturer(item, item.manufacturer, true);

        let name = this.translate(item.itemName);
        let manufacturer = this.translate(item.manufacturer);

        let selectText = name;
        if (category.toLowerCase() === 'seal') {
            selectText = item.size + " " + (addManufacturer ? manufacturer + " " : "") + name;
        } else if (addManufacturer) {
            selectText = manufacturer + " " + name;
        }
        return this.translate(category) + " " + selectText;
    },
    
    isAddManufacturer(item, manufacturer, selectText) {
        if (shared.isEmpty(manufacturer) || manufacturer.toLowerCase() === "ussr") {
            return false;
        }
        if (selectText) {
            return true;
        }
        let name = item.itemName;
        if (name === manufacturer + name.replace(manufacturer, "")
            || name === name.replace(manufacturer, "") + manufacturer) {
            return false;
        }

        let category = item.itemCategory;
        return category.toLowerCase() === 'seal' || category.toLowerCase() === 'spark plug';
    },

    isInArrayById(itemId, items) {
        for (let i=0; i < items.length; i++) {
            if (items[i].itemId === itemId) {
                return true;
            }
        }
        return false;
    }
}