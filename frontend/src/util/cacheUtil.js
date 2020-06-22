import shared from "./shared";
import storeUtil from "./storeUtil";

export default {
    removeCachedViews(itemId, cachedViews) {
        let foundViews = this.findViewsInCache(itemId, cachedViews);
        for (let i = 0; i < foundViews.length; i++) {
            shared.removeFromArray(foundViews[i], cachedViews);
            console.log("removed: " + foundViews[i].lang)
        }
        storeUtil.setCachedViews(cachedViews);
    },

    findViewsInCache(itemId, cachedViews) {
        let foundViews = [];
        for (let i = 0; i < cachedViews.length; i++) {
            console.log(cachedViews[i].itemId);
            console.log(itemId);
            let viewFound = cachedViews[i].itemId == itemId;
            console.log("view found: " + viewFound);
            console.log("----------");
            if (viewFound) {
                foundViews.push(cachedViews[i]);
            }
        }
        console.log("views found: " + foundViews.length);
        console.log("----------");
        return foundViews;
    },
}