<template>
    <div id="app_area">
        <table style="border-spacing: 0px; text-align: right" v-show="isLastComponent('MotorcycleMenu')">
            <tbody>
            <tr>
                <td style="text-align: left">
                    <button type="button" v-on:click="openWishList()">
                        {{"Wishlist: " + wishList.count + " items"}}
                    </button>
                </td>
                <td>
                    {{userName}}
                </td>
            </tr>
            <tr v-if="admin">
                <td></td>
                <td>
                    {{"You are admin"}}
                </td>
            </tr>
            </tbody>
        </table>

        <MotorcycleMenu
                v-show="isLastComponent('MotorcycleMenu')"
                @select-item="selectItem"
                @add-motorcycle="addMotorcycle"/>

        <ModelPartsList v-show="isLastComponent('ModelPartsList')" :motorcycle="motorcycle" :engine="engine"/>

        <AddMotorcycle v-show="isLastComponent('AddMotorcycle')" @refresh-motorcycles="refresh()"/>

        <Users v-show="isLastComponent('Users')" @reopen-users="reopenUsers()"/>

        <Report v-show="isLastComponent('Report')"/>

        <WishList v-show="isLastComponent('WishList')" @select-item="selectItem"/>

        <Item v-show="isLastComponent('Item')" @select-item="selectItem"/>

        <BearingList v-show="isLastComponent('Bearings')" @reopen-bearings="reopenBearings()"/>

        <SealList v-show="isLastComponent('Seals')" @reopen-seals="reopenSeals()"/>

        <table class="centred-table" v-show="isLastComponent('MotorcycleMenu')">
            <tbody>
            <tr>
                <td colspan="1">
                    Additional
                </td>
            </tr>
            <tr v-if="admin">
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="openUsers()">
                        Users
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="openReports()">
                        Reports
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="openBearings()">
                        Bearings
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="openSeals()">
                        Seals
                    </button>
                </td>
            </tr>
            </tbody>
        </table>

        <div v-show="isLastComponent('MotorcycleMenu')"
             style="width: 100%; text-align: center; margin-top: 60px; margin-bottom: 20px">
            Minsk 2019
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import MotorcycleMenu from "./MotorcycleMenu";
    import BearingList from "./BearingList";
    import SealList from "./SealList";
    import ModelPartsList from "./ModelPartsList";
    import AddMotorcycle from "./AddMotorcycle";
    import Report from "./Report";
    import Users from "./Users";
    import WishList from "./WishList";
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
            this.refreshUsers();
            this.refreshBearings();
            this.refreshSeals();
            this.refreshWishList(this.userName);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                homeComponent: state => state.dictionary.homeComponent,
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
                wishList: state => state.dictionary.wishList,
                table: state => state.dictionary.table
            })
        },

        components: {
            MotorcycleMenu,
            BearingList,
            SealList,
            ModelPartsList,
            AddMotorcycle,
            Report,
            Users,
            WishList,
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

            addMotorcycle() {
                this.switchComponent('AddMotorcycle');
            },

            openWishList() {
                this.switchComponent("WishList");
            },

            reopenWishList() {
                this.refreshWishList(this.userName);
                this.openWishList();
            },

            openUsers() {
                this.switchComponent('Users');
            },

            reopenUsers() {
                this.refreshUsers();
                this.openUsers();
            },

            openReports() {
                this.switchComponent('Report');
            },

            openBearings() {
                this.switchComponent('Bearings');
            },

            reopenBearings() {
                this.refreshBearings();
                this.refreshWishList(this.userName);
                this.openBearings();
            },

            openSeals() {
                this.switchComponent('Seals')
            },

            reopenSeals() {
                this.refreshSeals();
                this.openSeals();
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
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
                this.switchComponent('MotorcycleMenu');
            },

            refreshBearings() {
                axios
                    .get(`/backend/bearing/list`, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setBearings", response.data)
                    });
            },

            refreshSeals() {
                axios
                    .get(`/backend/seal/list`, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setSeals", response.data)
                    });
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

            refreshItems(motorcycleName) {
                axios
                    .get("backend/motorcycle/" + motorcycleName + "/items", {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setWishList", response.data)
                    })

            },

            setItem(id) {
                axios
                    .get("backend/item/" + id, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => this.$store.dispatch("addItemView", response.data));
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