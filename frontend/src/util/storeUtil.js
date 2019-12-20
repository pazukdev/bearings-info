export default {
    setEditMode(store, editMode) {
        store.dispatch("setEditMode", editMode);
    },

    setLoadingState(store, loadingState) {
        store.dispatch("setLoadingState", loadingState);
    }
}