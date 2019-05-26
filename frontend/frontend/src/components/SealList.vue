<template>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Rotation</th>
                <th scope="col">Material</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(bearing, index) in seals" :key="index">
                <td>{{bearing.name}}</td>
                <td>{{bearing.rotation}}</td>
                <td>{{bearing.material}}</td>
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
                seals: []
            }
        },

        created() {
            axios
                .get(`/backend/seal/list`)
                .then(response => {
                    this.seals = response.data;
                });
        }
    }
</script>
<style scoped>

</style>