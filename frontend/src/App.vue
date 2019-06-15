<template id="app">
    <div id="background">
        <div id = "screen">

            <br/>
            <p id="appName"><b>Bearings info</b></p>
            <br/>
            <div id="navigationButtons">
                <button id="motorcyclesButton" @click="swapComponent(componentsArray[0])">Motorcycles</button>
                &nbsp;&nbsp;&nbsp;
                <button id="bearingsButton" @click="swapComponent(componentsArray[1])">Bearings</button>
                &nbsp;&nbsp;&nbsp;
                <button id="sealsButton" @click="swapComponent(componentsArray[2])">Seals</button>
            </div>
            <br/>
            <br/>
            <div :is="currentComponent"></div>
            <br/>
            <br/>
            <br/>
            <br/>
        </div>
    </div>
</template>

<script>
    import MotorcycleList from "./components/MotorcycleList";
    import BearingList from "./components/BearingList";
    import SealList from "./components/SealList";

    export default {
        name: 'app',

        data() {
            return {
                currentComponent: MotorcycleList,
                componentsArray: ['MotorcycleList', 'BearingList', 'SealList']
            }
        },

        components: {
            MotorcycleList,
            BearingList,
            SealList
        },

        methods: {
            swapComponent: function(component)
            {
                this.currentComponent = component;
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
        margin: auto;
        width: 60%;
    }
</style>
