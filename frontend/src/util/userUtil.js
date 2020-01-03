export default {
    isAuthorized(authorization) {
        return authorization !== "";
    },

    isGuest(userName) {
        return userName.toString() === "guest";
    },

    isAdmin(itemView) {
        return itemView.userData.role === "ADMIN";
    },

    isSeller(itemView) {
        return itemView.userData.role === "SELLER";
    },

    isCurrentUserItemCreator(currentUserName, itemCreatorName) {
        return currentUserName === itemCreatorName;
    }
}