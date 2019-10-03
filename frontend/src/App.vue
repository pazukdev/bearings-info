<template id="app">
    <div id="background">
        <div id="screen" style>
            <div id="app_bar" style="background-color: #617D89; height: 70px; padding: 10px">
                <table style="text-align: center; width: 100%; height: 100%">
                    <tbody>
                    <tr>
                        <td style="width: 80px">
                            <button
                                    v-show="isBackButtonDisplayed()"
                                    @click="back()"
                                    id="back"
                                    class="app-bar-button">
                                    <b>Back</b>
                            </button>
                        </td>
                        <td id="appName" style="text-align: center; font-size: x-large">
                            <b>Bearings info</b>
                        </td>
                        <td style="width: 80px">
                            <button
                                    v-show="isAuthorized()"
                                    @click="logout()"
                                    id="logout"
                                    class="app-bar-button">
                                <b>Logout</b>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
<!--            {{homeComponent}}<br>-->
<!--            {{itemViews.length}}<br>-->
<!--            {{itemIds}}-->
            <router-view style="padding: 20px"></router-view>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {
        name: 'app',

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                homeComponent: state => state.dictionary.homeComponent,
                itemViews: state => state.dictionary.itemViews,
                itemIds: state => state.dictionary.itemIds,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1],
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1]
            })
        },

        methods: {
            logout() {
                this.removeToken();
                this.reload();
                this.$store.dispatch("clearHistory");
            },

            removeToken() {
                let token = null;
                this.$store.dispatch("setAuthorization", token);
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            reload() {
                window.location.reload();
            },

            getCurrentRouteName() {
                return this.$router.currentRoute.name;
            },

            isBackButtonDisplayed() {
                return this.homeComponent.length > 1;
            },

            isBackAllowedUrl() {
                return this.getCurrentRouteName() !== "login"
                    && this.getCurrentRouteName() !== "home";
            },

            back() {
                if (this.homeComponent[this.homeComponent.length - 1] === "Item") {
                    this.$store.dispatch("removeLastItemView");
                    if (this.homeComponent[this.homeComponent.length - 1] === "Item") {
                        this.$store.dispatch("removeLastItemId");
                        this.refreshItem();
                    }

                }
                this.$store.dispatch("removeLastComponent");
                if (this.itemIds.length === 0) {
                    this.showMotorcycleMenu();
                }

                if (this.homeComponent.length === 1) {
                    this.refreshWishList(this.userName);
                }
            },

            refreshItem() {
                axios
                    .get("backend/item/" + this.itemId + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("removeLastItemView");
                        this.$store.dispatch("addItemView", response.data);
                    });
            },

            showMotorcycleMenu() {
                axios
                    .get("/backend/item/motorcycles", {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("addItemView", response.data)
                    });
            },

            refreshWishList(userName) {
                axios
                    .get("backend/" + userName + "/categorized-wishlist", {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setWishList", response.data)
                    })

            },
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #background {
        background-color: black;
        height: 1024px;
        padding-top: 50px;
    }

    #screen {
        background-color: aliceblue;
        margin: auto;
        width: 500px;
        height: 800px;
        overflow-y: auto;
        border-radius: 10px;
    }

    #screen::-webkit-scrollbar {
        /*display: none;*/
    }

    #appName {
        text-align: center;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
        font-size: larger;
        color: #252525
    }

    .centred-table {
        text-align: center;
    }

    .get-all-table {
        text-align: center;
    }

    .creation-form {
        text-align: left;
        border-spacing: 20px;
        border-collapse: separate;
    }

    .content {
        width: 100%;
    }

    .right {
        width: 50%;
    }

    .default-width-1 {
        width: 160px;
    }

    .default-width-2 {
        width: 100px;
    }

    .full-width {
        width: 100%;
    }

    .round-button {
        text-align: center;
        height: 32px;
        width: 32px;
        border-radius: 16px;
    }

    button {
        border-radius: 4px;
        border: none;
        background: #929292;
        height: 52px;
    }

    select {
        border-radius: 4px;
        text-indent: 10px;
        height: 36px;
    }

    input {
        border-radius: 4px;
        border: none;
        background: navajowhite;
        height: 36px;
        text-align: center;
    }

    table {
        width: 100%;
        text-align: left;
        margin-left:auto;
        margin-right:auto;
        border-spacing: 10px;
        border-collapse: separate;
    }

    th {
        font-weight: normal;
    }

</style>
