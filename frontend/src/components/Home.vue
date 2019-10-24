<template>
    <div id="app_area">
        <div v-if="isLoading()" style="text-align: center; margin-top: 240px">
            {{"Loading..."}}
        </div>

        <Item v-if="!isLoading()" @cancel="cancel" @select-item="selectItem"/>

        <table style="text-align: center" v-if="isHome() && !isLoading()">
            <tbody>
            <tr>
                <td>
                    <button class="content"
                            type="button"
                            style="width: 174px"
                            v-on:click="openItemsManagement()">
                        {{"Items management"}}
                    </button>
                </td>
            </tr>
            <tr v-if="admin">
                <td>
                    <button class="content"
                            type="button"
                            style="width: 174px"
                            v-on:click="openUsersList()">
                        {{"Users"}}
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <div style="text-align: center; margin-top: 60px; margin-bottom: 20px">
                        {{"Minsk 2019"}}
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import Item from "./Item";

    export default {

        data() {
            return {
                motorcycle: "",
                engine: "",
                manufacturers: [],
                engines: [],
                decodedJwtData: ""
            }
        },

        created() {
            this.refresh();
            this.isAdmin();
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
                loading: state => state.dictionary.loading,
                wishList: state => state.dictionary.wishList,
                table: state => state.dictionary.table,
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1],
                itemViews: state => state.dictionary.itemViews,
                itemIds: state => state.dictionary.itemIds,
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1]
            })
        },

        components: {
            Item
        },

        methods: {

            isLoading() {
                return this.loading === true || this.itemView === undefined;
            },

            isHome() {
                return this.itemIds.length === 1;
            },

            openSpecialView(specialItemId) {
                this.$store.dispatch("setLoading", true);
                this.$store.dispatch("addItemId", specialItemId);
                this.selectItem(specialItemId);
            },

            openItemsManagement() {
                let itemsManagementId = -1;
                this.openSpecialView(itemsManagementId);

            },

            openUsersList() {
                let usersListId = -4;
                this.openSpecialView(usersListId);
            },

            selectItem(id) {
                this.setItem(id);
            },

            getItemView(itemId, removeLastItemView) {
                axios
                    .get("/backend/item/get-view/" + itemId
                        + "/" + this.userName, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        if (removeLastItemView === true) {
                            this.$store.dispatch("removeLastComponent");
                            this.$store.dispatch("removeLastItemView");
                        }
                        this.$store.dispatch("addItemView", response.data);
                    });
            },

            getItem(id) {
                this.getItemView(id, false);
            },

            showMotorcycleMenu() {
                let specialMotorcycleCatalogueItemId = -2;
                this.$store.dispatch("addItemId", specialMotorcycleCatalogueItemId);
                this.getItemView(specialMotorcycleCatalogueItemId, false);
            },

            cancel(id) {
                this.getItemView(this.itemId, true);
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
            },

            setItem(id) {
                this.getItem(this.itemId);
            },

            redirectToLogin() {
                if (!this.isAuthorized()) {
                    this.goToLogin();
                }
            },

            goToLogin() {
                this.$router.push({ path: '/login'});
            },

            isAuthorized() {
                return this.authorization !== "";
            },

            isAdmin() {
                let jwtData = this.authorization.split('.')[1];
                let decodedJwtJsonData = window.atob(jwtData);
                let decodedJwtData = JSON.parse(decodedJwtJsonData);
                let isAdmin = decodedJwtData.roles[0] === "ROLE_ADMIN";
                this.$store.dispatch("setAdmin", isAdmin);
            }

        }
    }

</script>

<style scoped>
    table {
        border-spacing: 20px;
        border-collapse: separate;
    }
</style>

}