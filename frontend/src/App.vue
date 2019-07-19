<template id="app">
    <div id="background">
        <div id="screen" style>
            <div id="app_bar" style="background-color: #617D89; height: 70px; padding: 10px">
                <table style="text-align: center; width: 100%; height: 100%">
                    <tbody>
                    <tr>
                        <td style="width: 80px">
                            <button
                                    v-show="component !== 'MotorcycleMenu'"
                                    @click="refresh()"
                                    id="back"
                                    style="width: 100%; height: 100%; background: none; font-size: larger; color: #252525">
                                    <b>Back</b>
                            </button>
                        </td>
                        <td id="appName" style="text-align: center; font-size: x-large">
                            <b>Bearings info</b>
                        </td>
                        <td style="width: 80px"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="app_area" style="padding: 10px">
                <MotorcycleMenu
                        v-show="component === 'MotorcycleMenu'"
                        @select-motorcycle="selectMotorcycle"
                        @add-motorcycle="addMotorcycle"
                        :motorcyclesList="motorcycles"/>

                <ModelPartsList v-show="component === 'ModelPartsList'" :motorcycle="motorcycle"/>

                <AddMotorcycle
                        v-show="component === 'AddMotorcycle'"
                        @refresh-motorcycles="refresh()"/>

                <Login v-show="component === 'Login'"/>
            </div>
            <table>
                <tbody>
                <tr>
                    <td>
                        <button
                                v-show="false"
                                style="width: 100%">
                            <b>AAA</b>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import MotorcycleMenu from "./components/MotorcycleMenu";
    import MotorcycleList from "./components/MotorcycleList";
    import BearingList from "./components/BearingList";
    import SealList from "./components/SealList";
    import ModelPartsList from "./components/ModelPartsList"
    import AddMotorcycle from "./components/AddMotorcycle"
    import Login from "./components/Login"

    export default {
        name: 'app',

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
                .get("backend/manufacturer/list")
                .then(response => this.manufacturers = response.data);
        },

        components: {
            MotorcycleMenu,
            MotorcycleList,
            BearingList,
            SealList,
            ModelPartsList,
            AddMotorcycle,
            Login
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
                //this.component = 'MotorcycleMenu';
                this.component = 'Login';
                this.getMotorcycles();
            },

            getMotorcycles() {
                this.motorcycles = axios.get(`/backend/motorcycle/list`).then(response => this.motorcycles = response.data);
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
        margin-top: 60px;
    }

    #background {
        background-color: black;
        height: 1024px;
        padding-top: 50px;
    }

    #screen {
        background-color: aliceblue;
        margin: auto;
        width: 480px;
        height: 700px;
        overflow-y: auto;
        border-radius: 10px;
    }

    #screen::-webkit-scrollbar {
        display: none;
    }

    #appName {
        text-align: center;
    }

    #navigationButtons {
        width: 200px;
    }

    .navigationButton {
        width: 100%;
    }

    button {
        border-radius: 4px;
        border: none;
        background: #929292;
    }

    input {
        border-radius: 4px;
        border: none;
        background: navajowhite;
    }

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

</style>
