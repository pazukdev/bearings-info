<template>
    <div style="text-align: center">
<!--        {{user}}-->
        <p>{{"User"}}</p>
        <table class="equal-columns-table">
            <tbody>
            <tr><td>{{"Name"}}</td><td>{{user.name}}</td></tr>
            <tr><td>{{"Role"}}</td><td>{{user.role}}</td></tr>
            <tr><td>{{"Rating"}}</td><td>{{user.rating}}</td></tr>
            <tr><td>{{"Email"}}</td><td>{{user.email}}</td></tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import storeUtil from "../util/storeUtil";
    import routerUtil from "../util/routerUtil";

    export default {
        name: "User",

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization
            })
        },

        data() {
            return {
                user: ""
            }
        },

        created() {
            this.getView();
        },

        methods: {
            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "user"
                        + "/" + routerUtil.getId(this.$route), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        this.user = response.data;
                        console.log("user rendered: name: " + this.user.name);
                        storeUtil.setLoadingState(this.$store, false);
                    });
            }
        }
    }
</script>

<style scoped>
    table {
        text-align: left;
    }
</style>