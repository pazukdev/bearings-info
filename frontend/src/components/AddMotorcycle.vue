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
                        <input class="content" v-model="motorcycleName" type="text" placeholder="name"/>
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
                        Weight:
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleWeightG" type="text" placeholder="weight, g"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="right">
                        <button class="content" type="button" v-on:click="submit">Create motorcycle</button>
                    </td>
                </tr>
                <tr style="height: 400px"></tr>
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
                manufacturers: []
            }
        },

        created() {
            axios
                .get(`/backend/manufacturer/list`)
                .then(response => this.manufacturers = response.data)
        },

        methods: {
            submit() {
                this.createMotorcycle();
            },

            getManufacturerName(manufacturerId)  {
                return this.manufacturers.filter(function(manufacturer){
                    return (manufacturer.id === manufacturerId)
                })[0].name;
            },

            createMotorcycle() {
                let newMotorcycle = {
                    name: this.motorcycleName,
                    manufacturerId: this.motorcycleManufacturerId,
                    weightG: this.motorcycleWeightG,
                    bearingDtos: [],
                    productionStartYear: 1917,
                    productionStopYear: 1993
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
        width: 600px;
    }

</style>
