import shared from "./shared";

export default {
    filterItem(item, filter) {
        if (shared.isEmpty(filter)) {
            return true;
        }

        filter = filter.toLowerCase();

        return this.checkIncludes(item.manufacturer, filter)
            || this.checkIncludes(item.itemCategory, filter)
            || this.checkIncludes(item.vehicleClass, filter)
            || this.checkIncludes(item.buttonText, filter)
            || this.checkIncludes(item.comment, filter)
            || this.checkEquals(item.creatorName, filter);
    },

    checkIncludes(value, filter) {
        if (shared.isEmpty(value)) {
            return false;
        }
        return value.toLowerCase().includes(filter);
    },

    checkEquals(value, filter) {
        if (shared.isEmpty(value)) {
            return false;
        }
        return value.toLowerCase() === filter;
    }

}