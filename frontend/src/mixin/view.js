import userUtil from "../util/userUtil";
import {mapState} from "vuex";
import itemViewUtil from "../util/itemViewUtil";
import axiosUtil from "../util/axiosUtil";
import shared from "../util/shared";

export default {
    computed: {
        ...mapState({
            itemView: state => state.dictionary.itemView,
            userData: state => state.dictionary.userData,
            editMode: state => state.dictionary.editMode
        })
    },

    methods: {
        isGuest() {
            return userUtil.isGuest();
        },

        isEditor() {
            return userUtil.isEditor();
        },

        isSeller() {
            return userUtil.isSeller();
        },

        isAdmin() {
            return userUtil.isAdmin();
        },

        isManufacturer() {
            return itemViewUtil.isManufacturer(this.itemView);
        },

        isEditable(item) {
            if (!this.editMode) {
                return false;
            }
            return this.isAdmin() || this.isEditor() || shared.isEmpty(item.id);
        },

        getUserName() {
            return userUtil.getUserName();
        },

        isAuthorized() {
            return itemViewUtil.isAuthorized(axiosUtil.getAuthorization());
        },

        isOrdinaryItem(itemView) {
            let category = itemView.category.toLowerCase();
            return !(category === "standard" || this.isManufacturer(itemView));
        }
    }
}