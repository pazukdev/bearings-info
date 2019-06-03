<template>
    <div>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<b>Bearings</b></p>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Type</th>
                <th scope="col">Rolling element</th>
                <th scope="col">Rolling elements quantity</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(bearing, index) in bearings" :key="index">
                <td>{{bearing.name}}</td>
                <td>{{bearing.type}}</td>
                <td>{{bearing.rollingElement}}</td>
                <td>{{bearing.rollingElementsQuantity}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                bearings: []
            }
        },

        created() {
            axios
                .get(`/backend/bearing/list`)
                .then(response => {
                    this.bearings = response.data;
                });
        }
    }
</script>
<style scoped>

</style>
