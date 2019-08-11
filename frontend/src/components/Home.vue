<template>
    <div id="app_area">
        <table style="border-spacing: 0px; text-align: right" v-show="homeComponent === 'MotorcycleMenu'">
            <tbody>
            <tr>
                <td>
                    {{userName}}
                </td>
            </tr>
            <tr v-if="admin">
                <td>
                    {{"You are admin"}}
                </td>
            </tr>
            </tbody>
        </table>

        <MotorcycleMenu
                v-show="homeComponent === 'MotorcycleMenu'"
                @select-motorcycle="selectMotorcycle"
                @add-motorcycle="addMotorcycle"/>

        <ModelPartsList v-show="homeComponent === 'ModelPartsList'" :motorcycle="motorcycle" :engine="engine"/>

        <AddMotorcycle v-show="homeComponent === 'AddMotorcycle'" @refresh-motorcycles="refresh()"/>

        <Users v-show="homeComponent === 'Users'"/>

        <Report v-show="homeComponent === 'Report'"/>

        <BearingList v-show="homeComponent === 'Bearings'" @reopen-bearings="reopenBearings()"/>

        <SealList v-show="homeComponent === 'Seals'"/>

        <table class="centred-table" v-show="homeComponent === 'MotorcycleMenu'">
            <tbody>
            <tr>
                <td colspan="1">
                    Additional
                </td>
            </tr>
            <tr v-if="admin">
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="users()">
                        Users
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <button class="content" type="button" style="width: 160px" v-on:click="reports()">
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
<!--            <tr>-->
<!--                <td>-->
<!--                    <button class="content" type="button" style="width: 160px" v-on:click="seals()">-->
<!--                        Seals-->
<!--                    </button>-->
<!--                </td>-->
<!--            </tr>-->
            </tbody>
        </table>

        <div v-show="homeComponent === 'MotorcycleMenu'"
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
            this.manufacturers = axios
                .get("backend/manufacturer/list", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.manufacturers = response.data);
            this.engines = axios
                .get("backend/engine/list", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.engines = response.data);

            this.isAdmin();
            this.refreshBearings();
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                homeComponent: state => state.dictionary.homeComponent,
                userName: state => state.dictionary.userName,
                admin: state => state.dictionary.admin,
            })
        },

        components: {
            MotorcycleMenu,
            BearingList,
            SealList,
            ModelPartsList,
            AddMotorcycle,
            Report,
            Users
        },

        methods: {
            switchComponent(component) {
                this.$store.dispatch("setHomeComponent", component);
            },

            selectMotorcycle(motorcycle) {
                this.motorcycle = motorcycle;
                this.engine = this.getEngine([motorcycle.engineId]);
                this.switchComponent('ModelPartsList');
            },

            addMotorcycle() {
                this.switchComponent('AddMotorcycle');
            },

            users() {
                this.switchComponent('Users')
            },

            reports() {
                this.switchComponent('Report')
            },

            openBearings() {
                this.switchComponent('Bearings')
            },

            reopenBearings() {
                this.refreshBearings();
                this.bearings();
            },

            seals() {
                this.switchComponent('Seals')
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
            },

            showMotorcycleMenu() {
                this.getMotorcycles();
                this.switchComponent('MotorcycleMenu');
            },

            getMotorcycles() {
                axios
                    .get(`/backend/motorcycle/list`, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.$store.dispatch("setMotorcycles", response.data)
                    });
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

            getEngine(ids) {
                axios
                    .post('backend/engine/search', ids, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => this.engine = response.data[0]);

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