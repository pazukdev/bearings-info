import shared from "./shared";

export default {
    filterItem(item, filter) {
        if (shared.isEmpty(filter)) {
            return true;
        }

        filter = filter.toLowerCase();

        if (item.creatorName.toLowerCase() === filter) {
            return true;
        }

        if (item.buttonText.toLowerCase().includes(filter)) {
            return true;
        }

        if (item.selectText.toLowerCase().includes(filter)) {
            return true;
        }

        if (item.itemCategory.toLowerCase().includes(filter)) {
            return true;
        }

        return false;
    },
}