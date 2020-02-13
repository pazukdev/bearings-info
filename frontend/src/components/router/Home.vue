<template>
    <div>
        <details class="default-margin">
            <summary>{{translate("About the app")}}</summary>
            <div class="default-margin">
                {{translate(aboutTheApp())}}
                <hr>
            </div>
        </details>
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
    import dictionaryUtil from "../../util/dictionaryUtil";
    import Info from "../info/Info";

    export default {
        name: "Home",

        components: {Info, NewsSection, DefaultButton, LoadingScreen, MotorcycleCatalogue},

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
                        + "/" + this.userName
                        + "/" + this.appLanguage.toString(), {
                        headers: {
                            Authorization: this.authorization
                        }
                    })
                    .then(response => {
                        itemViewUtil.dispatchView(response.data);
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
                return itemViewUtil.isAdmin(this.itemView);
            },

            isLoading() {
                return shared.isLoading(this.loadingState);
            },

            translate(text) {
                return dictionaryUtil.translate(text);
            },

            aboutTheApp() {
                return "Information about seals, bearings and other parts of soviet boxers and some other old or "
                    + "rare vehicles";
            }
        }
    }
</script>

<style scoped>

</style>