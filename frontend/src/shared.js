export default {
    isInArray(element, array) {
        for (let i=0; i < array.length; i++) {
            if (array[i] === element) {
                return true;
            }
        }
        return false;
    },

    messageIsEmpty(message) {
        return message === "";
    },

    statusIsActive(status) {
        return status === "active";
    },

    removeFromArray(element, array) {
        array.splice(array.indexOf(element), 1);
    },

}