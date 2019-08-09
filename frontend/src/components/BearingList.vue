<template>
    <div>
        <br/>
        <p id="title" style="text-align: center;"><b>Bearings</b></p>

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
            <tr v-for="(bearing, index) in bearingzz" :key="index">
                <td>{{bearing.name}}</td>
                <td>{{bearing.type}}</td>
                <td>{{bearing.rollingElement}}</td>
                <td>{{bearing.rollingElementsQuantity}}</td>
            </tr>
            </tbody>
        </table>

        <table class="creation-form">
            <tbody>
            <tr style="text-align: center">
                <td colspan="2">
                    <b>Create bearing</b>
                </td>
            </tr>
            <tr>
                <td>
                    Name
                </td>
                <td class="right">
                    <input class="content" v-model="name" type="text"/>
                </td>
            </tr>
            <tr>
                <td>
                    Type
                </td>
                <td class="right">
                    <select class="content" v-model ="type">
                        <option v-for="type in types">
                            {{type}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Rolling element
                </td>
                <td class="right">
                    <select class="content" v-model="rollingElement">
                        <option v-for="rollingElement in rollingElements">
                            {{rollingElement}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    Rollin elements quantity
                </td>
                <td class="right">
                    <input v-model="rollingElementsQuantity" type="text"/>
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
</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {

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
                .get(`/backend/bearing/list`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.bearings = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization,
                bearingzz: state => state.dictionary.bearings
            })
        },

        methods: {
            submit() {
                let newBearing = {
                    name: this.name,
                    type: this.type,
                    rollingElement: this.rollingElement,
                    rollingElementsQuantity: this.rollingElementsQuantity
                };

                axios.post(`/backend/bearing/create`, newBearing, {
                    headers: {
                        Authorization: this.authorization
                    }
                }).then(response => {
                    this.$emit('reopen-bearings');
                });
            },

            clearForm() {
                this.name = "";
                this.type = "";
                this.rollingElement = "";
                this.rollingElementsQuantity = "";
            }
        }
    }
</script>
<style scoped>

</style>
