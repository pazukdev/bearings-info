<template id="app">
    <div id="background">
        <div id = "screen">
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;<button id="back" v-show="modelIsSelected()" @click="unselectModel">Back</button>
                <div id="appName"><b>Bearings info</b></div>
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

            <MotorcycleMenu v-show="!modelIsSelected()" @select-motorcycle="selectMotorcycle"/>
            <ModelPartsList v-show="motorcycleId !== 0" :motorcycleId="motorcycleId"/>

        </div>
    </div>
</template>

<script>
    import MotorcycleMenu from "./components/MotorcycleMenu";
    import MotorcycleList from "./components/MotorcycleList";
    import BearingList from "./components/BearingList";
    import SealList from "./components/SealList";
    import ModelPartsList from "./components/ModelPartsList"

    export default {
        name: 'app',

        data() {
            return {
                //currentComponent: MotorcycleMenu,
                componentsArray: ['MotorcycleList', 'BearingList', 'SealList', 'ModelPartsList'],
                motorcycleId: 0
            }
        },

        components: {
            MotorcycleMenu,
            MotorcycleList,
            BearingList,
            SealList,
            ModelPartsList
        },

        methods: {
            selectMotorcycle(motorcycleId) {
                this.motorcycleId = motorcycleId;
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
</style>
