<template>
    <div>
        <br/>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;<b id="title">Seals</b></p>

        &nbsp;
        <input v-model="name" type="text" placeholder="name"/>
        &nbsp;
        <select v-model="rotation">
            <option v-for="rotation in rotations">
                {{rotation}}
            </option>
        </select>
        &nbsp;
        <select v-model="material">
            <option v-for="material in materials">
                {{material}}
            </option>
        </select>
        &nbsp;
        <button id="addButton" type="button" v-on:click="submit">Add Seal</button>

        <table id="productsTable" class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Rotation</th>
                <th scope="col">Material</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(bearing, index) in seals" :key="index">
                <td>{{bearing.name}}</td>
                <td>{{bearing.rotation}}</td>
                <td>{{bearing.material}}</td>
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
                seals: [],
                name: "",
                rotation: "",
                material: "",
                rotations: ["left", "right"],
                materials: ["rubber"]
            }
        },

        created() {
            axios
                .get(`/backend/seal/list`)
                .then(response => {
                    this.seals = response.data;
                });
        },

        methods: {
            submit() {
                let newSeal = {
                    name: this.name,
                    rotation: this.rotation,
                    material: this.material
                };

                axios.post(`/backend/seal/create`, newSeal);
                this.motorcycles.push(newSeal);

            }
        }
    }
</script>
<style scoped>

</style>