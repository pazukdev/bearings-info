<template id="app">
    <div id="main-div">
        <div class="mobile-hide" style="height: 1%"></div>
        <div id="screen">
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
<!--            <div style="width: 100%; text-align: left">-->
<!--                                {{"Item views stack length: " + itemViews.length}}<br>-->
<!--                                {{"Item ids: " + itemIds}}<br>-->
<!--                                {{"Is loading: " + loading}}<br>-->
<!--                                {{"is admin: " + admin}}<br>-->
<!--                                {{"itemView: " + itemView}}<br>-->
<!--                                {{"itemId: " + itemId}}<br>-->
<!--                                <div v-if="itemView !== null || itemView !== undefined">-->
<!--                                    {{"itemView.itemId: " + itemView.itemId}}<br>-->
<!--                                </div>-->
<!--            </div>-->
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
                loading: state => state.dictionary.loading,
                itemIds: state => state.dictionary.itemIds,
                itemView: state => state.dictionary.itemView,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1],
                admin: state => state.dictionary.admin
            })
        },

        methods: {
            logout() {
                this.removeToken();
                this.reload();
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

            isBackButtonDisplayed() {
                return this.itemIds.length > 1 && !this.loading;
            },

            back() {
                this.$store.dispatch("setLoading", true);
                this.$store.dispatch("removeLastItemView");
                this.$store.dispatch("removeLastItemId");
                this.getItemView(this.itemId);
            },

            getItemView(itemId) {
                axios
                    .get("backend/item/get-view/" + itemId
                        + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setItemView", response.data);
                    });
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        height: 100%;
    }

    #main-div {
        background: black;
        height: 2000px;
    }

    #screen {
        background-color: #212121;
        color: #808080;
        margin: auto;
        width: 480px;
        height: 800px;
        overflow-y: auto;
        border-radius: 10px;
    }

    @media only screen and (max-width: 1280px) {
        #main-div {
            background: #212121;
        }

        .mobile-hide {
            display: none;
        }

        #screen {
            width: 100%;
            height: 1024px;
            border-radius: 0;
        }

        input {
            width: 100%;
        }

        button {
            width: 100%;
        }
    }

    #screen::-webkit-scrollbar {
        display: none;
    }

    #appName {
        text-align: center;
        color: #212121;
    }

    .app-bar-button {
        width: 100%;
        height: 100%;
        background: none;
        font-size: larger;
        color: #212121
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

    .half-width {
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

    .no-border {
        border-spacing: 0;
    }

    .third-part-wide {
        width: 33.33%;
    }

    button {
        border-radius: 4px;
        border: none;
        background: #808080;
        height: 52px;
        width: 146px;
    }

    select {
        border-radius: 4px;
        text-indent: 10px;
        height: 36px;
        background: #808080;
    }

    input {
        border-radius: 4px;
        border: none;
        background: #617D89;
        height: 36px;
        text-align: center;
    }

    table {
        width: 100%;
        text-align: left;
        margin-left:auto;
        margin-right:auto;
        border-spacing: 6px;
        border-collapse: separate;
    }

    th {
        font-weight: normal;
    }

    hr {
        background: #808080;
    }

</style>
