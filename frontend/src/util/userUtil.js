import shared from "./shared";

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

    isSeller(role) {
        if (shared.isEmpty(role)) {
            return false;
        }
        return role.toLowerCase() === "seller";
    },

    isCurrentUserItemCreator(currentUserName, itemCreatorName) {
        return currentUserName === itemCreatorName;
    }
}