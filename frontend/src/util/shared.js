export default {
    isInArray(element, array) {
        for (let i=0; i < array.length; i++) {
            if (array[i] === element) {
                return true;
            }
        }
        return false;
    },

    isEmpty(value) {
        return value === "-" || value === "" || value === null || value === undefined;
    },

    messageIsEmpty(message) {
        return this.isEmpty(message);
    },

    statusIsActive(status) {
        return status === "active";
    },

    removeFromArray(element, array) {
        array.splice(array.indexOf(element), 1);
    },

    isNestedItemsTitleVisible(nestedItemsList, editMode) {
        return nestedItemsList.length > 0 || editMode;
    },

    isLoading(loadingState) {
        return !this.isEmpty(loadingState);
    }

}