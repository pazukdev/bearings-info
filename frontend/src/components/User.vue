<template>
    <div>
        {{user}}
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import storeUtil from "../storeUtil";
    import routerUtil from "../routerUtil";

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

</style>