export default {

    sortByName(array) {
        return array.slice().sort((a,b) => (a.name > b.name) ? 1 : -1);
    },

    sortBySelectText(array) {
        return array.slice().sort((a,b) => (a.selectText > b.selectText) ? 1 : -1);
    },

    sortByWeight(array) {
        return array.slice().sort(function (a, b) {
            return b.weight - a.weight || a.name.localeCompare(b.name);
        });
    },

    sortByComment(array) {
        return array.slice().sort(function (a, b) {
            return a.comment.localeCompare(b.comment)
                || a.buttonText.localeCompare(b.buttonText)
                || a.secondComment.localeCompare(b.secondComment);
        });
    },

    countVehicles(vehicleClasses) {
        let count = 0;
        for (let i = 0; i < vehicleClasses.length; i++) {
            for (let j = 0; j < vehicleClasses[i].manufacturers.length; j++) {
                for (let k = 0; k < vehicleClasses[i].manufacturers[j].items.length; k++) {
                    count++;
                }
            }
        }
        return count;
    }

}