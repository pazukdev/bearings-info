<template id="app">
    <div>
        <Header/>
        <br/>
        <MotorcycleList/>
        <br/>
        <BearingList/>
        <br/>
        <SealList/>
        <br/>
    </div>
</template>

<script>
    import Header from "./components/Header";
    import MotorcycleList from "./components/MotorcycleList";
    import BearingList from "./components/BearingList";
    import SealList from "./components/SealList";

    export default {
        name: 'app',
        components: {
            Header,
            MotorcycleList,
            BearingList,
            SealList
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
</style>
