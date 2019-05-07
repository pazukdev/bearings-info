<template>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Unit</th>
                <th scope="col">Quantity</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(bearing, index) in bearings" :key="index">
                <td>{{bearing.id}}</td>
                <td>{{bearing.name}}</td>
                <td>{{bearing.unit}}</td>
                <td>{{bearing.quantity}}</td>
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
            axios.get(`/backend/bearing/list`)
                .then(response => {
                    this.bearings = response.data;
                });
        }
    }
</script>
<style scoped>

</style>
