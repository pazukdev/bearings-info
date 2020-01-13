<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <div v-else>
            <Header :item="false"/>
            <ItemList :user-list-view="true"/>
        </div>
    </div>
</template>

<script>
    import LoadingScreen from "../special/LoadingScreen";
    import ItemList from "./ItemList";
    import axios from "axios";
    import itemViewUtil from "../../util/itemViewUtil";
    import {mapState} from "vuex";
    import Header from "./section/Header";

    export default {
        name: "UserList",
        components: {Header, ItemList, LoadingScreen},

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                authorization: state => state.dictionary.authorization,
                userName: state => state.dictionary.userName,
                appLanguage: state => state.dictionary.appLanguage,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState
            })
        },

        created() {
            this.onUrlChange();
        },

        watch: {
            '$route': 'onUrlChange'
        },

        methods: {
            onUrlChange() {
                this.getView();
            },

            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "user/list"
                        + "/" + this.userName
                        + "/" + this.appLanguage, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(this.$store, response.data);
                        console.log("user list rendered");
                    });
            },
        }

    }
</script>

<style scoped>

</style>