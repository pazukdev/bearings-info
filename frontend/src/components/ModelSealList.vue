<template>
    <div>
        <p id="title" class="centredText"><b>Seals</b></p>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Rotation</th>
                <th scope="col">Material</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(seal, index) in seals" :key="index">
                <td>{{seal.name}}</td>
                <td>{{seal.rotation}}</td>
                <td>{{seal.material}}</td>
            </tr>
            </tbody>
        </table>

    </div>

</template>

<script>
    import axios from 'axios';
    import {mapState} from 'vuex';

    export default {
        name: "ModelSealList.vue",

        props: ['engine'],

        data() {
            return {
                seals: [],
                name: "",
                rotation: "",
                material: "",
                rotations: ["left", "right"],
                materials: ["rubber"],
                motorcycle: ""
            }
        },

        created() {
            axios
                .all([
                    this.getAllSeals()
                ])
                .then(axios.spread((sealsResponse) => {
                    this.seals = sealsResponse.data;
                }));
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization
            })
        },

        methods: {
            getAllSeals() {
                return axios.get(`/backend/seal/list`, {
                    headers: {
                        Authorization: this.authorization
                    }
                })
            }
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