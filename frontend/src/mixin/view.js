import userUtil from "../util/userUtil";
import {mapState} from "vuex";
import itemViewUtil from "../util/itemViewUtil";
import axiosUtil from "../util/axiosUtil";

export default {
    computed: {
        ...mapState({
            itemView: state => state.dictionary.itemView,
            userData: state => state.dictionary.userData
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
            return userUtil.isAdmin(this.itemView);
        },

        isManufacturer() {
            return itemViewUtil.isManufacturer(this.itemView);
        },

        getUserName() {
            return userUtil.getUserName();
        },

        isAuthorized() {
            return itemViewUtil.isAuthorized(axiosUtil.getAuthorization());
        }

    }
}