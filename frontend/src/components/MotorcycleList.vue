<template>
    <div>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<b>Motorcycles</b></p>

        <button type="button" v-on:click="submit">Add Motorcycle</button>
        <input v-model="name" type="text" placeholder="name"/>
        <input v-model="manufacturer" type="text" placeholder="manufacturer"/>
        <input v-model="weightG" type="text" placeholder="weight, g"/>

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
                motorcycles: [],
                name: "",
                manufacturer: "",
                weightG: ""
            }
        },

        created() {
            axios
                .get(`/backend/motorcycle/list`)
                .then(response => {
                    this.motorcycles = response.data;
                });
        },

        methods: {
            submit() {
                let newMotorcycle = {
                    name: this.name,
                    manufacturer: this.manufacturer,
                    weightG: this.weightG
                };

                axios.post(`/backend/motorcycle/create`, newMotorcycle);
                this.motorcycles.push(newMotorcycle);

            }
        }
    }

</script>
<style scoped>

</style>