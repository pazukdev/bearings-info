<template>
    <div>
        <div>
            <table class="creation-form">
                <tbody>
                <tr style="text-align: center">
                    <td colspan="2">
                        <b>Create motorcycle</b>
                    </td>
                </tr>
                <tr>
                    <td>
                        Name
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleName" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Manufacturer
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
                        Engine
                    </td>
                    <td class="right">
                        <select class="content" v-model ="engineId">
                            <option v-for="engine in engines" v-bind:value="engine.id">
                                {{engine.name}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Production started, year
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleProductionStartYear" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Production stopped, year
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleProductionStopYear" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Weight, g
                    </td>
                    <td class="right">
                        <input class="content" v-model="motorcycleWeightG" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Fuel capacity, l
                    </td>
                    <td class="right">
                        <input class="content" v-model="fuelCapacityL" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="right">
                        <button class="content" type="button" v-on:click="submit()">Create</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {

        data() {
            return {
                motorcycleName: "",
                motorcycleManufacturerId: "",
                motorcycleWeightG: "",
                motorcycleProductionStartYear: "",
                motorcycleProductionStopYear: "",
                engineId: "",
                fuelCapacityL: "",
                manufacturers: [],
                engines: []
            }
        },

        created() {
            axios
                .get(`/backend/manufacturer/list`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.manufacturers = response.data);
            axios
                .get(`/backend/engine/list`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.engines = response.data)
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                motorcycles: state => state.dictionary.motorcycles
            })
        },

        methods: {
            submit() {
                this.createMotorcycle();
            },

            createMotorcycle() {
                let newMotorcycle = {
                    name: this.motorcycleName,
                    manufacturerId: this.motorcycleManufacturerId,
                    engineId: this.engineId,
                    weightG: this.motorcycleWeightG,
                    productionStartYear: this.motorcycleProductionStartYear,
                    productionStopYear: this.motorcycleProductionStopYear,
                    fuelCapacityL: this.fuelCapacityL
                };

                axios.post(`/backend/motorcycle/create`, newMotorcycle, {
                    headers: {
                        Authorization: this.authorization
                    }
                }).then(response => {
                    this.$emit('refresh-motorcycles');
                });
            }
        }
    }
</script>

<style scoped>

</style>
