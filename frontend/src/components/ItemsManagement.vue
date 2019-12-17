<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <ItemList v-else :items-management-view="true"/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";
    import axios from "axios";
    import {mapState} from "vuex";
    import LoadingScreen from "./LoadingScreen";

    export default {
        name: "ItemsManagement",

        components: {
            LoadingScreen,
            ItemList
        },

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
            this.getView();
        },

        methods: {
            getView() {
                // this.$store.dispatch("setLoadingState", true);
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "get-items-management-view"
                        + "/" + this.userName
                        + "/" + this.$i18n.locale, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        let itemView = response.data;
                        this.dispatchView(itemView);
                        console.log("items management displayed");
                    });
            },

            dispatchView(itemView) {
                this.$store.dispatch("setItemView", itemView);
                this.$store.dispatch("setLoadingState", false);
            }
        }
    }
</script>

<style scoped>

</style>