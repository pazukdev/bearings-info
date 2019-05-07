<template id="app">
    <div>
        <Header/>
        <BearingList/>
    </div>
</template>

<script>
    import BearingList from "./components/BearingList";
    import Header from "./components/Header";

    export default {
        name: 'app',
        components: {
            Header,
            BearingList
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
