<template id="app">
    <div>
        <table style="width: 100%">
            <tbody>
            <tr class="mobile-hide" style="height: 50px"><td colspan="3"></td></tr>
            <tr>
                <td class="mobile-hide"></td>
                <td style="width: 1%; white-space: nowrap">
                    <div id="mobile-screen">
                        <table>
                            <tbody>
                            <tr style="background: #617D89; height: 80px">
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
                            <tr>
                                <td colspan="3">
                                    <div style="text-align: left">
                                        <!--                                    {{"Item ids: " + itemIds}}<br>-->
                                        <!--                                    {{"Is loading: " + loading}}<br>-->
                                        <!--                                    {{"is admin: " + admin}}<br>-->
                                        <!--                                    {{"itemView: " + itemView}}<br>-->
                                        <!--                                    {{"itemId: " + itemId}}<br>-->
                                        <!--                <div v-if="itemView !== null || itemView !== undefined">-->
                                        <!--                    {{"itemView.itemId: " + itemView.itemId}}<br>-->
                                        <!--                </div>-->
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <router-view>
                                    </router-view>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
                <td class="mobile-hide"></td>
            </tr>
            </tbody>
        </table>
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
    }

    #background {
        background-color: black;
        height: 1024px;
        padding-top: 50px;
    }

    #mobile-screen {
        background-color: #212121;
        color: #808080;
        text-align: center;
        width: 480px;
        height: 800px;
        overflow-y: auto;
        margin: auto;
    }

    #mobile-screen::-webkit-scrollbar {
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

    .mobile-hide {
        background: black;
    }

    @media only screen and (max-width: 1000px) {
        .mobile-hide {
            display: none;
        }
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

    button {
        border-radius: 4px;
        border: none;
        background: #808080;
        height: 52px;
        width: 148px;
    }

    select {
        border-radius: 4px;
        text-indent: 10px;
        width: 160px;
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
        text-align: center;
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

    img {
        max-width: 100%;
    }

</style>
