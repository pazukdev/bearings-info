<template>
    <div>
        <p id="title" style="text-align: center"><b>Create motorcycle</b></p>

        <div>
            <table>
                <tbody>
                <tr>
                    <td>
                        Name:
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleName" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Manufacturer:
                    </td>
                    <td class="right">
                        <select class="content" v-model ="motorcycleManufacturerId">
                            <option v-for="manufacturer in manufacturers" v-bind:value="manufacturer.id">
                                {{manufacturer.name}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Production started, year:
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleProductionStartYear" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Production stopped, year:
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleProductionStopYear" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Weight, g:
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleWeightG" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Bearings:
                    </td>
                    <td>
                        <table>
                            <thead>
                            <tr>
                                <th>
                                    <label class="form-checkbox">
                                        <input type="checkbox" v-model="selectAll" @click="select">
                                    </label>
                                </th>
                                <th>all</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="(bearing, index) in bearings" :key="index">
                                <td>
                                    <label class="form-checkbox">
                                        <input type="checkbox" :value="bearing.id" v-model="selected">
                                    </label>
                                </td>
                                <td>{{bearing.name}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="right">
                        <button class="content" type="button" v-on:click="submit">Create</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {

        name: "AddMotorcycle.vue",

        data() {
            return {
                motorcycleName: "",
                motorcycleManufacturerId: "",
                motorcycleWeightG: "",
                motorcycleProductionStartYear: "",
                motorcycleProductionStopYear: "",
                manufacturers: [],
                bearings: [],
                selected: [],
                selectAll: false
            }
        },

        created() {
            axios
                .get(`/backend/manufacturer/list`)
                .then(response => this.manufacturers = response.data);
            axios
                .get(`/backend/bearing/list`)
                .then(response => this.bearings = response.data)
        },

        methods: {
            submit() {
                this.createMotorcycle();
            },

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.bearings) {
                        this.selected.push(this.bearings[i].id);
                    }
                }
            },

            createMotorcycle() {
                let newMotorcycle = {
                    name: this.motorcycleName,
                    manufacturerId: this.motorcycleManufacturerId,
                    weightG: this.motorcycleWeightG,
                    productionStartYear: this.motorcycleProductionStartYear,
                    productionStopYear: this.motorcycleProductionStopYear,
                    bearingIds: this.selected
                };

                return axios.post(`/backend/motorcycle/create`, newMotorcycle);
            },
        }
    }
</script>

<style scoped>

    table {
        text-align: left;
        border-spacing: 20px;
        border-collapse: separate;
        margin-left:auto;
        margin-right:auto;
    }

    .content {
        width: 100%;
    }

    .right {
        width: 40%;
    }

</style>
