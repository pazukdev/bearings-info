<template>
    <div>
        <p id="title" class="centredText"><b>Bearings</b></p>
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

        props: ['motorcycle'],

        data() {
            return {
                bearings: []
            }
        },

        watch: {
            motorcycle(newVal) {
                this.bearings = this.getBearings(newVal.bearingIds);
            }
        },

        methods: {
            getBearings(ids) {
                axios
                    .post('backend/bearing/search', ids)
                    .then(response => this.bearings = response.data);

            },
        }
    }

</script>
<style scoped>

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
    }

    th {
        font-weight: normal;
    }

    .centredText {
        text-align: center;
    }

</style>