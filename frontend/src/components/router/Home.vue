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
    import MotorcycleCatalogue from "../list/MotorcycleCatalogue";
    import LoadingScreen from "../special/LoadingScreen";
    import itemViewUtil from "../../util/itemViewUtil";
    import DefaultButton from "../element/button/DefaultButton";
    import NewsSection from "../info/NewsSection";
    import Info from "../info/Info";
    import userUtil from "../../util/userUtil";
    import routerUtil from "../../util/routerUtil";
    import axiosUtil from "../../util/axiosUtil";
    import basicComponent from "../../mixin/basicComponent";
    import view from "../../mixin/view";

    export default {
        name: "Home",

        components: {
            Info,
            NewsSection,
            DefaultButton,
            LoadingScreen,
            MotorcycleCatalogue},

        mixins: [basicComponent, view],

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
            }

        }
    }
</script>

<style scoped>

</style>