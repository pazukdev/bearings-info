<template>
    <div>
        <p id="title">&nbsp;&nbsp;&nbsp;&nbsp;<b>Motorcycles</b></p>

        <div>
            <div>
                &nbsp;&nbsp;&nbsp;&nbsp;Name:
                <input v-model="motorcycleName" type="text" placeholder="name"/>
                <br/>
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;Manufacturer:
                <select v-model ="motorcycleManufacturerId">
<!--                    <option v-for="o in obj" v-bind:value="o">{{o.text}}</option>-->
                    <option v-for="manufacturer in manufacturers" v-bind:value="manufacturer.id">
                        {{manufacturer.name}}
                    </option>
                </select>
                <br/>
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;Weight:
                <input v-model="motorcycleWeightG" type="text" placeholder="weight, g"/>
                <br/>
                <br/>ght
                &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" v-on:click="submit">Create motorcycle</button>
                <br/>
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" v-on:click="remove">Remove motorcycle</button>
                <br/>
            </div>
        </div>

        <br/>

        <table id="productsTable" class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
                    <label class="form-checkbox">
                        <input type="checkbox" v-model="selectAll" @click="select">
                    </label>
                </th>
                <th>Name</th>
                <th>Manufacturer</th>
                <th>Weight, g</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="motorcycle in motorcycles" :key="motorcycle.id">
                <td>
                    <label class="form-checkbox">
                        <input type="checkbox" :value="motorcycle.id" v-model="selected">
                    </label>
                </td>
                <td>{{motorcycle.name}}</td>
                <td>{{getManufacturerName(motorcycle.manufacturerId)}}</td>
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
                motorcycleName: "",
                motorcycleManufacturerId: "",
                motorcycleWeightG: "",
                manufacturers: [],
                selected: [],
                selectAll: false
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

        methods: {

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.motorcycles) {
                        this.selected.push(this.motorcycles[i].id);
                    }
                }
            },

            submit() {
                axios
                    .all([
                    this.createMotorcycle(),
                    this.getAllMotorcycles()
                    ])
                    .then(axios.spread((firstResponse, secondResponse) => {
                        this.motorcycles = secondResponse.data
                    }));
                this.reload(); // TODO find the way how to update the table without page reload
            },

            remove() {
                axios
                    .all([
                        this.deleteSelectedMotorcycles(),
                        this.getAllMotorcycles()
                    ])
                    .then(axios.spread((firstResponse, secondResponse) => {
                        this.motorcycles = secondResponse.data
                    }));
                this.reload(); // TODO find the way how to update the table without page reload
            },

            getManufacturerName(manufacturerId)  {
                return this.manufacturers.filter(function(manufacturer){
                    return (manufacturer.id === manufacturerId)
                })[0].name;
            },

            reload() {
                window.location.reload();
            },

            createMotorcycle() {
                let newMotorcycle = {
                    name: this.motorcycleName,
                    manufacturerId: this.motorcycleManufacturerId,
                    weightG: this.motorcycleWeightG
                };

                return axios.post(`/backend/motorcycle/create`, newMotorcycle);
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

</style>