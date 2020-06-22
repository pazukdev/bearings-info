import userUtil from "../util/userUtil";
import {mapState} from "vuex";
import itemViewUtil from "../util/itemViewUtil";
import axiosUtil from "../util/axiosUtil";
import shared from "../util/shared";
import cacheUtil from "../util/cacheUtil";

export default {
    computed: {
        ...mapState({
            itemView: state => state.dictionary.itemView,
            userData: state => state.dictionary.userData,
            editMode: state => state.dictionary.editMode,
            cachedViews: state => state.dictionary.cachedViews
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
        },

        dispatchCachedView(cachedView, lang) {
            console.log("got itemView from cache");
            cachedView.userData = this.userData;
            cachedView.businessLogicTime = 0;
            cachedView.translationTime = 0;
            cachedView.responseTotalTime = 0;
            cachedView.enableUpdate = true;
            itemViewUtil.dispatchView(cachedView, lang);
        },

        // viewIsCached(itemId, lang) {
        //     return !shared.isEmpty(this.findViewsInCache(itemId, lang));
        // },

        findViewInCache(itemId, lang, reportType) {
            let views = cacheUtil.findViewsInCache(itemId, this.cachedViews);
            for (let i = 0; i < views.length; i++) {
                let viewFound = views[i].lang === lang && views[i].reportType === reportType;
                if (viewFound) {
                    return views[i];
                }
            }
            return null;
        },

        // findViewsInCache(itemId) {
        //     let views = [];
        //     for (let i = 0; i < this.cachedViews.length; i++) {
        //         console.log(this.cachedViews[i].itemId);
        //         console.log(itemId);
        //         let viewFound = this.cachedViews[i].itemId == itemId;
        //         console.log("view found: " + viewFound);
        //         console.log("----------");
        //         if (viewFound) {
        //             views.push(this.cachedViews[i]);
        //         }
        //     }
        //     console.log("views found: " + views.length);
        //     console.log("----------");
        //     return views;
        // },

        // removeCachedViews(itemId) {
        //     let cachedViews = this.findViewsInCache(itemId);
        //     for (let i = 0; i < cachedViews.length; i++) {
        //         shared.removeFromArray(cachedViews[i], this.cachedViews);
        //         console.log("removed: " + cachedViews[i].lang)
        //     }
        //     storeUtil.setCachedViews(this.cachedViews);
        // }
    }
}