import axios from "axios";
import routerUtil from "./routerUtil";
import store from "../plugins/store";
import itemViewUtil from "./itemViewUtil";

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
                        routerUtil.toHome();
                    }
                }
            });
    },

    logout(basicUrl) {
        routerUtil.toLogin();
        console.log("logout");
        let toHome = false;
        this.loginAsGuest(basicUrl, toHome);
    },

    updateItem(itemId, itemView, basicUrl, userName, appLanguage, authorization) {
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
                itemViewUtil.dispatchView(updatedItemView);
                console.log("item updated");
            });
    }

}