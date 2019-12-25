import axios from "axios";
import routerUtil from "./routerUtil";
import store from "../plugins/store";
import itemViewUtil from "./itemViewUtil";
import router from "../plugins/router";

export default {
    loginAsGuest(basicUrl, toHome) {
        let username = "guest";
        let password = "guest";
        let credentialsUrl ="username=" + username + "&" + "password=" + password;
        axios
            .post(basicUrl + "/login", credentialsUrl)
            .then(response => {
                if (response.status === 200) {
                    let authorization = response.data.Authorization;
                    store.dispatch("setAuthorization", authorization);
                    store.dispatch("setUserName", username);
                    console.log("logged in as " + username);
                    if (toHome) {
                        routerUtil.toHome(router);
                    }
                }
            });
    },

    updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization, store) {
        axios
            .put(basicUrl
                + "/" + "item"
                + "/" + "update"
                + "/" + itemId
                + "/" + userName
                + "/" + appLanguage,
                itemView, {
                    headers: {
                        Authorization: authorization
                    }
                })
            .then(response => {
                let updatedItemView = response.data;
                itemViewUtil.dispatchView(store, updatedItemView);
                console.log("item updated");
            });
    },

}