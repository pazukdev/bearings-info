<template>
    <div>
        <p><b id="title">Seals</b></p>
        <div>{{motorcycleId}}</div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Rotation</th>
                <th scope="col">Material</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(seal, index) in seals" :key="index">
                <td>{{seal.name}}</td>
                <td>{{seal.rotation}}</td>
                <td>{{seal.material}}</td>
            </tr>
            </tbody>
        </table>

    </div>

</template>

<script>
    import axios from 'axios';

    export default {
        name: "ModelSealList.vue",

        props: ['motorcycleId'],

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
                .all([
                    this.getAllSeals()
                ])
                .then(axios.spread((sealsResponse) => {
                    this.seals = sealsResponse.data;
                }));
        },

        methods: {

            getAllSeals() {
                return axios.get(`/backend/seal/list`)
            }

        }
    }

</script>
<style scoped>

</style>