<template id="app">
    <div id="background">
        <div id="screen" style>
            <div id="app_bar" style="background-color: #617D89; height: 70px; padding: 10px">
                <table style="text-align: center; width: 100%; height: 100%">
                    <tbody>
                    <tr>
                        <td style="width: 80px">
                            <button
                                    v-show="modelIsSelected() || add" @click="unselectModel"
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
                <div id="navigationButtons" v-show="false">
                    <button class="navigationButton" id="motorcyclesButton" @click="swapComponent(componentsArray[0])">
                        Motorcycles
                    </button>
                    <button class="navigationButton" id="bearingsButton" @click="swapComponent(componentsArray[1])">
                        Bearings
                    </button>
                    <button class="navigationButton" id="sealsButton" @click="swapComponent(componentsArray[2])">
                        Seals
                    </button>
                </div>

                <MotorcycleMenu v-show="!modelIsSelected() && add === false" @select-motorcycle="selectMotorcycle" @add-motorcycle="addMotorcycle"/>
                <ModelPartsList v-show="motorcycleId !== 0" :motorcycleId="motorcycleId"/>
                <AddMotorcycle v-show="add"/>
            </div>
        </div>
    </div>
</template>

<script>
    import MotorcycleMenu from "./components/MotorcycleMenu";
    import MotorcycleList from "./components/MotorcycleList";
    import BearingList from "./components/BearingList";
    import SealList from "./components/SealList";
    import ModelPartsList from "./components/ModelPartsList"
    import AddMotorcycle from "./components/AddMotorcycle"

    export default {
        name: 'app',

        data() {
            return {
                add: false,
                componentsArray: ['MotorcycleList', 'BearingList', 'SealList', 'ModelPartsList, AddMotorcycle'],
                motorcycleId: 0
            }
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
            selectMotorcycle(motorcycleId) {
                this.motorcycleId = motorcycleId;
            },

            addMotorcycle(val) {
                this.add = true;
            },

            swapComponent: function(component) {
                this.currentComponent = component;
                this.componentDisplayed = true;
            },

            modelIsSelected() {
                return this.motorcycleId !== 0;
            },

            unselectModel() {
                this.motorcycleId = 0;
                this.add = false;
                this.reload();
            },

            reload() {
                window.location.reload();
            },
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

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

    #triangle-left {
        width: 0;
        height: 0;
        border-top: 50px solid transparent;
        border-right: 100px solid red;
        border-bottom: 50px solid transparent;
    }


</style>
