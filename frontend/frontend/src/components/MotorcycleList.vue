<template>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Manufacturer</th>
                <th scope="col">Weight, g</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(motorcycle, index) in motorcycles" :key="index">
                <td>{{motorcycle.name}}</td>
                <td>{{motorcycle.manufacturer}}</td>
                <td>{{motorcycle.weightG}}</td>
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
                motorcycles: []
            }
        },

        created() {
            axios
                .get(`/backend/motorcycle/list`)
                .then(response => {
                    this.motorcycles = response.data;
                });
        }
    }
</script>
<style scoped>

</style>