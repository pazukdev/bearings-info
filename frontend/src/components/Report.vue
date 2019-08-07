<template>
    <div>
        <p id="title" style="text-align: center"><b>Reports</b></p>

        <div>
            <table style="width: 100%">
                <tbody style="width: 100%">
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
                        <button class="content" type="button" v-on:click="getSpeedReport(motorcycleId)">Get Speed report</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="content" type="button" v-on:click="getFuelReport(motorcycleId)">Get Fuel report</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        {{report}}
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
                report: "",
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
                    }) .then(response => this.report = response.data)
            },

            getFuelReport(id) {
                axios
                    .get("/backend/motorcycle/" + id + "/fuel-report", {
                    headers: {
                        Authorization: this.authorization
                    }
                }) .then(response => this.report = response.data)
            },

            removeReport(val) {
                this.report = val;
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

    tr {
        width: 100%;
    }

    td {
        width: 100%;
    }

    .content {
        width: 100%;
    }

</style>