<template id="app">
    <div id="background">
        <div id = "screen">
            <table id="productsTable" class="table">
                <tbody>
                <tr>
                    <td style="width: 80px">
                        <button id="back" style="width: 100%" v-show="modelIsSelected() || add" @click="unselectModel">
                            Back
                        </button>
                    </td>
                    <td><div id="appName" style="text-align: center"><b>Bearings info</b></div></td>
                    <td style="width: 80px"></td>
                </tr>
                </tbody>
            </table>
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;

            <br/>
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
        width: 35%;
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

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

    .centredText {
        text-align: center;
    }
</style>
