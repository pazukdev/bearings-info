<template>
    <div>
        <p id="title">&nbsp;&nbsp;&nbsp;&nbsp;<b>Motorcycles</b></p>

        <div>
            <div>
                &nbsp;&nbsp;&nbsp;&nbsp;Name:
                <input v-model="name" type="text" placeholder="name"/>
                <br/>
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;Manufacturer:
                <select v-model="manufacturer">
                    <option v-for="manufacturer in manufacturers">
                        {{manufacturer}}
                    </option>
                </select>
                <br/>
                <br/>
                &nbsp;&nbsp;&nbsp;&nbsp;Weight:
                <input v-model="weightG" type="text" placeholder="weight, g"/>
                <br/>
                <br/>
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
                        <i class="form-icon"></i>
                    </label>
                </th>
                <th>Name</th>
                <th>Manufacturer</th>
                <th>Weight, g</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="i in motorcycles">
                <td>
                    <label class="form-checkbox">
                        <input type="checkbox" :value="i.id" v-model="selected">
                        <i class="form-icon"></i>
                    </label>
                </td>
                <td>{{i.name}}</td>
                <td>{{i.manufacturer}}</td>
                <td>{{i.weightG}}</td>
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
                id: "",
                name: "",
                manufacturer: "",
                weightG: "",
                manufacturers: ["imz", "kmz"],
                selected: [],
                selectAll: false,
                i: ""
            }
        },

        created() {
            axios
                .get(`/backend/motorcycle/list`)
                .then(response => {
                    this.motorcycles = response.data;
                });
        },

        methods: {

            select() {
                this.selected = [];
                if (!this.selectAll) {
                    for (let i in this.motorcycles) {
                        this.i = i;
                        this.id = this.motorcycles[this.i].id;
                        this.selected.push(this.id);
                    }
                }
            },

            submit() {
                let newMotorcycle = {
                    name: this.name,
                    manufacturer: this.manufacturer,
                    weightG: this.weightG
                };

                axios.post(`/backend/motorcycle/create`, newMotorcycle);
                this.motorcycles.push(newMotorcycle);
            },

            remove() {
                console.log("Removing", this.id);
                axios.delete('/backend/motorcycle/{id}', this.id);
                //this.motorcycles.splice(this.i, 1);
            }

        }
    }

</script>
<style scoped>

</style>