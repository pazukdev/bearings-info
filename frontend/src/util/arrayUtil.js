export default {

    sortByName(array) {
        return array.slice().sort((a,b) => (a.name > b.name) ? 1 : -1);
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
    }

}