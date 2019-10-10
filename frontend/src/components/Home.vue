<template>
    <div id="app_area">
        <MotorcycleMenu
                v-show="isLastComponent('MotorcycleMenu')"
                @select-item="selectItem"
                @cancel="cancel"
                @add-motorcycle="addMotorcycle"/>

        <Users v-show="isLastComponent('Users')" @reopen-users="reopenUsers"/>

        <Report v-show="isLastComponent('Report')"/>

        <Item v-show="isLastComponent('Item')"
              @cancel="cancel"
              @select-item="selectItem"/>

<!--        <table class="centred-table" v-show="isLastComponent('MotorcycleMenu')">-->
<!--            <tbody>-->
<!--            <tr v-if="admin">-->
<!--                <td>-->
<!--                    <button class="content"-->
<!--                            type="button"-->
<!--                            style="width: 174px"-->
<!--                            v-on:click="openUsers()">-->
<!--                        {{"Users"}}-->
<!--                    </button>-->
<!--                </td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--        </table>-->

        <div v-show="isLastComponent('MotorcycleMenu')"
             style="width: 100%; text-align: center; margin-top: 60px; margin-bottom: 20px">
            {{"Minsk 2019"}}
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import MotorcycleMenu from "./MotorcycleMenu";
    import Report from "./Report";
    import Users from "./Users";
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
            //this.refreshUsers();
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                homeComponent: state => state.dictionary.homeComponent,
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
                wishList: state => state.dictionary.wishList,
                table: state => state.dictionary.table,
                itemId: state => state.dictionary.itemIds[state.dictionary.itemIds.length - 1],
                itemView: state => state.dictionary.itemViews[state.dictionary.itemViews.length - 1]
            })
        },

        components: {
            MotorcycleMenu,
            Report,
            Users,
            Item
        },

        methods: {

            isLastComponent(component) {
                return this.homeComponent[this.homeComponent.length - 1] === component;
            },

            switchComponent(component) {
                this.$store.dispatch("setHomeComponent", component);
            },

            selectItem(id) {
                this.setItem(id);
                this.switchComponent("Item");
            },

            getItemView(itemId, removeLastItemView) {
                axios
                    .get("backend/item/get-view/" + itemId
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
                this.getItemView(specialMotorcycleCatalogueItemId, false);
                this.switchComponent('MotorcycleMenu');
            },

            cancel(id) {
                this.getItemView(this.itemId, true);
                this.switchComponent("Item");
            },

            addMotorcycle() {
                this.switchComponent('AddMotorcycle');
            },

            openUsers() {
                this.switchComponent('Users');
            },

            reopenUsers() {
                this.refreshUsers();
                this.openUsers();
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
            },

            refreshUsers() {
                axios
                    .get(`/backend/admin/user/list`, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setUsers", response.data)
                    });
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