import store from "../plugins/store";

export default {
    setUserName(userName, itemView) {
        store.dispatch("setUserName", userName);
        itemView.userData.name = userName;
    },

    setUserData(user) {
        store.dispatch("setUserData", user);
    },

    userIsBlocked() {
        return store.getters.userData.status === "blocked";
    },

    setEditMode(editMode) {
        store.dispatch("setEditMode", editMode);
    },

    setLoadingState(loadingState) {
        store.dispatch("setLoadingState", loadingState);
    },

    setLoadingStateDefault() {
        this.setLoadingState("Loading");
    },

    setLoadingStateOff() {
        this.setLoadingState("");
    },

    setErrorMessage(message) {
        store.dispatch("setErrorMessage", message);
    }
}