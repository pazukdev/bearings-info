<template>
    <div>
        <Info/>
        <NewsSection/>

        <LoadingScreen v-if="isLoading()"/>
        <MotorcycleCatalogue v-else/>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import MotorcycleCatalogue from "../list/MotorcycleCatalogue";
    import LoadingScreen from "../special/LoadingScreen";
    import itemViewUtil from "../../util/itemViewUtil";
    import DefaultButton from "../element/button/DefaultButton";
    import NewsSection from "../info/NewsSection";
    import shared from "../../util/shared";
    import Info from "../info/Info";
    import userUtil from "../../util/userUtil";
    import routerUtil from "../../util/routerUtil";
    import axiosUtil from "../../util/axiosUtil";

    export default {
        name: "Home",

        components: {
            Info,
            NewsSection,
            DefaultButton,
            LoadingScreen,
            MotorcycleCatalogue},

        computed: {
            ...mapState({
                basicUrl: state => state.dictionary.basicUrl,
                itemView: state => state.dictionary.itemView,
                loadingState: state => state.dictionary.loadingState
            })
        },

        data() {
            return {
                admin: false
            }
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
                        + "/" + "item/view"
                        + "/" + "home"
                        + "/" + userUtil.getUserName()
                        + "/" + routerUtil.getLang(this.$route), {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data, this.$route.params.lang);
                        console.log("home displayed");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            },

            logEvent(event, itemId, name) {
                console.log(event + ": " + "id=" + itemId + "; name=" + name);
            },

            isAdmin() {
                return userUtil.isAdmin(this.itemView);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            }
        }
    }
</script>

<style scoped>

</style>