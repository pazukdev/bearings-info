<template>
    <div>
        <p><b id="title">Bearings</b></p>
        <div>{{motorcycleId}}</div>
        <table id="productsTable" class="table">
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
        name: "ModelBearingList.vue",

        props: ['motorcycleId'],

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
                .all([
                    this.getAllBearings()
                ])
                .then(axios.spread((bearingsResponse) => {
                    this.bearings = bearingsResponse.data;
                }));
        },

        methods: {
            getAllBearings() {
                return axios.get(`/backend/bearing/list`)
            }
        }
    }

</script>
<style scoped>

</style>