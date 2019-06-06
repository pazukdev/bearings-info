<template>
    <div>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<b>Motorcycles</b></p>

        &nbsp;
        <input v-model="name" type="text" placeholder="name"/>
        &nbsp;
        <select v-model="manufacturer">
            <option v-for="manufacturer in manufacturers">
                {{manufacturer}}
            </option>
        </select>
        &nbsp;
        <input v-model="weightG" type="text" placeholder="weight, g"/>
        &nbsp;
        <button type="button" v-on:click="submit">Add Motorcycle</button>

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
                weightG: "",
                manufacturers: ["imz", "kmz"],
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