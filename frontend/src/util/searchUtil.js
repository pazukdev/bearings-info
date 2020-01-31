import shared from "./shared";

export default {
    filterItem(item, filter) {
        if (shared.isEmpty(filter)) {
            return true;
        }

        filter = filter.toLowerCase();

        let comment = item.comment;
        if (comment !== null && comment.toLowerCase().includes(filter)) {
            return true;
        }

        let creatorName = item.creatorName;
        if (creatorName !== null && creatorName.toLowerCase() === filter) {
            return true;
        }

        let buttonText = item.buttonText;
        if (buttonText !== null && buttonText.toLowerCase().includes(filter)) {
            return true;
        }

        let selectText = item.selectText;
        if (selectText !== null && selectText.toLowerCase().includes(filter)) {
            return true;
        }

        let category = item.itemCategory;
        if (category !== null && category.toLowerCase().includes(filter)) {
            return true;
        }

        let vehicleClass = item.translatedVehicleClass;
        if (vehicleClass !== null && vehicleClass.toLowerCase().includes(filter)) {
            return true;
        }

        return false;
    },
}