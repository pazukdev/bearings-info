<template>
    <div id="app_area" style="padding: 10px">
        <p>{{incorrectCredentials}}</p>
        <p>{{authorization}}</p>
        <p>{{manufacturers}}</p>
        <MotorcycleMenu
                v-show="component === 'MotorcycleMenu'"
                @select-motorcycle="selectMotorcycle"
                @add-motorcycle="addMotorcycle"
                :motorcyclesList="motorcycles"/>

        <ModelPartsList v-show="component === 'ModelPartsList'" :motorcycle="motorcycle"/>

        <AddMotorcycle
                v-show="component === 'AddMotorcycle'"
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
                component: "",
                motorcycle: "",
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
                incorrectCredentials: state => state.dictionary.incorrectCredentials
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
            selectMotorcycle(motorcycle) {
                this.motorcycle = motorcycle;
                this.component = 'ModelPartsList';
            },

            addMotorcycle() {
                this.component = 'AddMotorcycle';
            },

            reload() {
                window.location.reload();
            },

            refresh() {
                this.showMotorcycleMenu();
            },

            showMotorcycleMenu() {
                this.component = 'MotorcycleMenu';
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
            }
        }
    }

</script>