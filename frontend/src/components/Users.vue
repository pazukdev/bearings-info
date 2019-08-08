<template>
    <div>
        <p id="title" style="text-align: center"><b>Users</b></p>

        <div>
            <table style="text-align: left">
                <tbody>
                <tr style="text-align: left" v-for="user in users" :key="user.id">
                    <td>
                        {{user.name}}
                    </td>
                    <td>
                        {{user.role}}
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
                users: []
            }
        },

        created() {
            axios
                .get("/backend/admin/user/list", {
                    headers: {
                        Authorization: this.authorization
                    }
                })
                .then(response => this.users = response.data);
        },

        computed: {
            ...mapState({
                authorization: state => state.dictionary.authorization
            })
        },

        methods: {

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

</style>