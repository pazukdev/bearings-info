<template>
    <div>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<b>Bearings</b></p>
        &nbsp;
        <input v-model="name" type="text" placeholder="name"/>
        &nbsp;
        <select v-model="type">
            <option v-for="type in types">
                {{type}}
            </option>
        </select>
        &nbsp;
        <select v-model="rollingElement">
            <option v-for="rollingElement in rollingElements">
                {{rollingElement}}
            </option>
        </select>
        &nbsp;
        <input v-model="rollingElementsQuantity" type="text" placeholder="rollingElementsQuantity"/>
        &nbsp;
        <button type="button" v-on:click="submit">Add Bearing</button>

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
                bearings: [],
                name: "",
                type: "",
                rollingElement: "",
                rollingElementsQuantity: "",
                types: ["deepgroove", "cylindrical roller", "tapered roller"],
                rollingElements: ["ball", "roller", "tapered roller"]
            }
        },

        created() {
            axios
                .get(`/backend/bearing/list`)
                .then(response => {
                    this.bearings = response.data;
                });
        },

        methods: {
            submit() {
                let newBearing = {
                    name: this.name,
                    type: this.type,
                    rollingElement: this.rollingElement,
                    rollingElementsQuantity: this.rollingElementsQuantity
                };

                axios.post(`/backend/bearing/create`, newBearing);
                this.bearings.push(newBearing);

            }
        }
    }
</script>
<style scoped>

</style>
