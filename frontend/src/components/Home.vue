<template>
    <div id="app_area" style="padding: 10px">
<!--        <p>{{homeComponent}}</p>-->
<!--        <p>{{authorization}}</p>-->
<!--        <p>{{manufacturers}}</p>-->
        <MotorcycleMenu
                v-show="homeComponent === 'MotorcycleMenu'"
                @select-motorcycle="selectMotorcycle"
                @add-motorcycle="addMotorcycle"
                :motorcyclesList="motorcycles"/>

        <ModelPartsList v-show="homeComponent === 'ModelPartsList'" :motorcycle="motorcycle" :engine="engine"/>

        <AddMotorcycle
                v-show="homeComponent === 'AddMotorcycle'"
                @refresh-motorcycles="refresh()"/>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';
    import MotorcycleMenu from "./MotorcycleMenu";
    import MotorcycleList from "./MotorcycleList";
    import BearingList from "./BearingList";
    import SealList from "./SealList";
    import ModelPartsList from "./ModelPartsList";
    import AddMotorcycle from "./AddMotorcycle";

    export default {

        data() {
            return {
                motorcycle: "",
                engine: "",
                motorcycles: "",
                manufacturers: ""
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
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                incorrectCredentials: state => state.dictionary.incorrectCredentials,
                homeComponent: state => state.dictionary.homeComponent
            })
        },

        components: {
            MotorcycleMenu,
            MotorcycleList,
            BearingList,
            SealList,
            ModelPartsList,
            AddMotorcycle
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

            reload() {
                window.location.reload();
            },

            refresh() {
                this.redirectToLogin();
                this.showMotorcycleMenu();
            },

            showMotorcycleMenu() {
                this.switchComponent('MotorcycleMenu');
                this.getMotorcycles();
            },

            getMotorcycles() {
                this.motorcycles = axios
                    .get(`/backend/motorcycle/list`, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => this.motorcycles = response.data);
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
            }
        }
    }

</script>