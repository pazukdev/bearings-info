<template>
    <div>
        <p id="title" style="text-align: center"><b>Reports</b></p>

        <div>
            <table class="centred-table">
                <tbody>
                <tr>
                    <td>
                        <select class="content" v-model ="motorcycleId" v-on:click="removeReport(empty)">
                            <option v-for="motorcycle in motorcycles" v-bind:value="motorcycle.id">
                                {{motorcycle.name}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="content" type="button" v-on:click="getSpeedReport(motorcycleId)">Speed report</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="content" type="button" v-on:click="getFuelReport(motorcycleId)">Fuel report</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <table>
                <tbody>
                <tr>
                    <td colspan="2" style="text-align: center">{{table.name}}</td>
                </tr>
                <tr style="text-align: left" v-for="row in table.matrix">
                    <td>
                        {{row[0]}}
                    </td>
                    <td>
                        {{row[1]}}
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
                motorcycles: [],
                motorcycleId: "",
                table: "",
                empty: ""
            }
        },

        created() {
            axios
                .get(`/backend/motorcycle/list`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.motorcycles = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization
            })
        },

        methods: {
            getSpeedReport(id) {
                axios
                    .get("/backend/motorcycle/" + id + "/speed-report", {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => this.table = response.data)
            },

            getFuelReport(id) {
                axios
                    .get("/backend/motorcycle/" + id + "/fuel-report", {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => this.table = response.data)
            },

            removeReport(val) {
                this.table = val;
            }
        }
    }
</script>

<style scoped>

    table {
        border-spacing: 20px;
        border-collapse: separate;
        margin-left:auto;
        margin-right:auto;
    }

    .content {
        width: 160px;
    }

</style>