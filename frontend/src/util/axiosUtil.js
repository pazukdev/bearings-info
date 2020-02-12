import axios from "axios";
import routerUtil from "./routerUtil";
import store from "../plugins/store";
import itemViewUtil from "./itemViewUtil";
import storeUtil from "./storeUtil";

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
    },

    setLangsAndDictionary() {
        axios
            .get(store.getters.basicUrl
                + "/" + "file"
                + "/" + "dictionary-data"
                + "/" + store.getters.appLanguage, {
                headers: {
                    Authorization: store.getters.authorization
                }
            })
            .then(response => {
                let dictionaryData = response.data;
                let langs = dictionaryData.langs;

                console.log("got langs: " + langs);
                storeUtil.setLangs(langs);
                storeUtil.setDictionary(dictionaryData.dictionary);
            });
    },

}