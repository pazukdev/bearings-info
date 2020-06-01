import axios from "axios";
import routerUtil from "./routerUtil";
import store from "../plugins/store";
import itemViewUtil from "./itemViewUtil";
import storeUtil from "./storeUtil";
import userUtil from "./userUtil";

export default {

    loginAsGuest(toHome, lang) {
        console.log("loginAsGuest(toHome, lang)");
        let guest = {
            id: "",
            name: "guest",
            password: "password-stub",
            role: "GUEST",
            status: "active",
            email: ""
        }
        this.login(guest, toHome, lang);
    },

    login(userData, toHome, lang) {
        storeUtil.setLoadingState("Logging in");
        console.log("login(userName, password, toHome, lang)");
        console.log("toHome: " + toHome);

        let credentialsUrl ="username=" + userData.name + "&" + "password=" + userData.password;
        userData.password = "-";
        axios
            .post(this.getBasicUrl() + "/login", credentialsUrl)
            .then(response => {
                if (response.status === 200) {
                    storeUtil.setIncorrectCredentials(false);
                    storeUtil.setAuthorization(response.data.Authorization);
                    storeUtil.setUserData(userData);
                    console.log("logged in as " + userUtil.getUserName());
                    if (toHome) {
                        if (userData.status === "pending") {
                            routerUtil.toAccountActivation(lang, null);
                        } else {
                            routerUtil.toHome(lang, "");
                        }
                    }
                }
                storeUtil.setLoadingStateOff();
            })
            .catch(error => {
                storeUtil.setIncorrectCredentials(true);
                storeUtil.setLoadingStateOff();
            });
    },

    logout(lang) {
        routerUtil.toLogin(lang);
        console.log("logout");
        this.loginAsGuest(false, lang);
    },

    updateItem(itemId, itemView, lang) {
        axios
            .put(this.getBasicUrl()
                + "/" + "item"
                + "/" + "update"
                + "/" + itemId
                + "/" + userUtil.getUserName()
                + "/" + lang,
                itemView, {
                    headers: {
                        Authorization: this.getAuthorization()
                    }
                })
            .then(response => {
                let updatedItemView = response.data;
                itemViewUtil.dispatchView(updatedItemView, lang);
                console.log("item updated");
            });
    },

    setLangsAndDictionary(lang) {
        axios
            .get(this.getBasicUrl()
                + "/" + "download"
                + "/" + "dictionary-data"
                + "/" + lang, {
                headers: {
                    Authorization: this.getAuthorization()
                }
            })
            .then(response => {
                let dictionaryData = response.data;
                let langs = dictionaryData.langs;
                console.log("got langs: " + langs);
                storeUtil.setLangs(langs);
                storeUtil.setDictionary(dictionaryData.dictionary);
                storeUtil.setDictionaryId(dictionaryData.dictionaryId);
            });
    },

    getBasicUrl() {
        return store.getters.basicUrl;
    },

    getAuthorization() {
        return store.getters.authorization;
    }

}