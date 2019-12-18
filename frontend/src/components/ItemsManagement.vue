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
    import itemViewUtil from "../itemViewUtil";

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
                axios
                    .get(this.basicUrl
                        + "/" + "item"
                        + "/" + "view"
                        + "/" + "items-management"
                        + "/" + this.userName
                        + "/" + this.$i18n.locale, {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(this.$store, response.data);
                        console.log("items management displayed");
                    });
            }
        }
    }
</script>

<style scoped>

</style>