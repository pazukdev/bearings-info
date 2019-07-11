<template>
    <div>
        <p id="title" class="centredText"><b>Motorcycles</b></p>

        <table id="motorcycleMenu">
            <thead>
            <tr>
                <th>Production</th>
                <th>Model</th>
                <th>Manufacturer</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="motorcycle in sortedMotorcycles" :key="motorcycle.id">
                <td>{{motorcycle.productionStartYear}} - {{motorcycle.productionStopYear}}</td>
                <td style="width: 160px">
                    <button class="motorcycleButton" @click="$emit('select-motorcycle', motorcycle.id)">
                        {{motorcycle.name}}
                    </button>
                </td>
                <td>{{getManufacturerName(motorcycle.manufacturerId)}}</td>
            </tr>
            <tr><td></td>
                <td></td>
                <td><button style="width: 100%" @click="$emit('add-motorcycle', 1)">Add</button></td></tr>
            </tbody>
        </table>
        <br/>
        <p class="centredText">Minsk 2019</p>
        <br/>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {

        name: "MotorcycleMenu.vue",

        data() {
            return {
                componentsArray: ['MotorcycleList', 'BearingList', 'SealList'],
                componentDisplayed: false,
                motorcycles: [],
                manufacturers: [],
                motorcycleId: "",
                motorcycleName: "",
                motorcycleManufacturerId: "",
                motorcycleWeightG: ""
            }
        },

        created() {
            axios
                .all([
                    this.getAllManufacturers(),
                    this.getAllMotorcycles()
                ])
                .then(axios.spread((firstResponse, secondResponse) => {
                    this.manufacturers = firstResponse.data;
                    this.motorcycles = secondResponse.data;
                }));
        },

        computed: {
            sortedMotorcycles() {
                function compare(a, b) {
                    if (a.productionStartYear < b.productionStartYear)
                        return -1;
                    if (a.productionStartYear > b.productionStartYear)
                        return 1;
                    return 0;
                }

                return this.motorcycles.sort(compare);
            }
        },

        methods: {

            getManufacturerName(manufacturerId)  {
                return this.manufacturers.filter(function(manufacturer){
                    return (manufacturer.id === manufacturerId)
                })[0].name;
            },

            deleteSelectedMotorcycles() {
                return axios.post('/backend/motorcycle/delete-all', this.selected);
            },

            getAllMotorcycles() {
                return axios.get(`/backend/motorcycle/list`)
            },

            getAllManufacturers() {
                return axios.get(`/backend/manufacturer/list`)
            }

        }
    }

</script>
<style scoped>

    table {
        text-align: center;
        margin-left:auto;
        margin-right:auto;
        border-spacing: 20px;
        border-collapse: separate;
    }

    th {
        font-weight: normal;
    }

    .centredText {
        text-align: center;
    }

    .motorcycleButton {
        width: 100%;
    }

</style>