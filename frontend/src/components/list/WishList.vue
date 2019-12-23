<template>
    <div>
        <LoadingScreen v-if="this.loadingState"/>
        <ItemList v-else :editable-comments="true"/>
    </div>
</template>

<script>
    import ItemList from "./ItemList";
    import LoadingScreen from "../special/LoadingScreen";
    import {mapState} from "vuex";
    import axios from "axios";
    import itemViewUtil from "../../util/itemViewUtil";

    export default {
        name: "WishList",
        components: {ItemList, LoadingScreen},

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
                itemViewUtil.setLocale(this.$router, this.$route, this.$i18n, this.appLanguage.toString());
                this.getView();
            },

            getView() {
                axios
                    .get(this.basicUrl
                        + "/" + "view"
                        + "/" + "wishlist"
                        + "/" + this.userName
                        + "/" + this.appLanguage.toString(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(this.$store, response.data);
                        console.log("user wishlist rendered");
                    });
            },
        }
    }
</script>

<style scoped>

</style>