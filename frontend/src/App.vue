<template id="app">
    <div>
        <table style="width: 100%">
            <tbody>
            <tr class="mobile-hide" style="height: 50px"><td colspan="3"></td></tr>
            <tr>
                <td class="mobile-hide"></td>
                <td class="mobile-screen">
                    <table style="height: 100%">
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
<!--                        <tr>-->
<!--                            <td colspan="3">-->
<!--                                <div style="text-align: left">-->
<!--                                    {{"Item views stack length: " + itemViews.length}}<br>-->
<!--                                    {{"Item ids: " + itemIds}}<br>-->
<!--                                    {{"Is loading: " + loading}}<br>-->
<!--                                    {{"authorization: " + authorization}}<br>-->
<!--                                    {{"is admin: " + admin}}<br>-->
<!--                                    {{"itemView: " + itemView}}<br>-->
<!--                                    {{"itemId: " + itemId}}<br>-->
<!--                                    &lt;!&ndash;                <div v-if="itemView !== null || itemView !== undefined">&ndash;&gt;-->
<!--                                    &lt;!&ndash;                    {{"itemView.itemId: " + itemView.itemId}}<br>&ndash;&gt;-->
<!--                                    &lt;!&ndash;                </div>&ndash;&gt;-->
<!--                                </div>-->
<!--                            </td>-->
<!--                        </tr>-->
                        <tr>
                            <td colspan="3">
                                <router-view style="padding: 20px"></router-view>
                            </td>
                        </tr>
                        </tbody>
                    </table>
<!--                    <div id="screen" style="text-align: center; width: 300px">-->
<!--                        <div style="background-color: #617D89; height: 70px; padding: 10px">-->
<!--                        </div>-->
<!--                    </div>-->
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
                itemViews: state => state.dictionary.itemViews,
                itemIds: state => state.dictionary.itemIds,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1],
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
                        this.$store.dispatch("addItemView", response.data);
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
        text-align: center;
        color: #2c3e50;
    }

    #background {
        background-color: black;
        height: 1024px;
        padding-top: 50px;
    }

    .mobile-screen {
        background-color: #212121;
        color: #808080;
        width: 480px;
        height: 800px;
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

    img {
        max-width: 100%;
    }

</style>
