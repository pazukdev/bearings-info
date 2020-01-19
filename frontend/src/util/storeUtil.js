import store from "../plugins/store";

export default {
    setUserName(userName, itemView) {
        store.dispatch("setUserName", userName);
        itemView.userData.name = userName;
    },

    setEditMode(editMode) {
        store.dispatch("setEditMode", editMode);
    },

    setLoadingState(loadingState) {
        store.dispatch("setLoadingState", loadingState);
    },

    setErrorMessage(errorMessage) {
        store.dispatch("setErrorMessage", errorMessage);
    }
}