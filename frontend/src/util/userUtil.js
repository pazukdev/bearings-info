import shared from "./shared";
import store from "../plugins/store";

export default {
    isAuthorized(authorization) {
        return authorization !== "";
    },

    isGuest() {
        return this.isUser("guest", this.getUserData());
    },

    isAdmin(itemView) {
        return this.isUser("admin", itemView.userData);
    },

    isEditor() {
        return this.isUser("editor", this.getUserData());
    },

    isSeller() {
        return this.isUser("seller", this.getUserData());
    },

    isUser(expectedRole, actualUserData) {
        if (shared.isEmpty(actualUserData)) {
            return false;
        }
        return this.isUserRole(expectedRole, actualUserData.role);
    },

    isUserRole(expectedRole, actualRole) {
        if (shared.isEmpty(actualRole) || shared.isEmpty(expectedRole)) {
            return false;
        }
        return actualRole.toLowerCase() === expectedRole.toLowerCase();
    },

    isCurrentUserItemCreator(itemCreatorId) {
        return store.getters.userData.id === itemCreatorId;
    },

    getUserName() {
        if (shared.isEmpty(this.getUserData().name)) {
            return "guest";
        }
        return this.getUserData().name;
    },

    getUserData() {
        return store.getters.userData;
    }

}