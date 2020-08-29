<template>
    <div>
        <LoadingScreen v-if="isLoading()"/>
        <transition name="slide-fade">
            <MotorcycleCatalogue v-if="!isLoading()"/>
        </transition>
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
import axiosUtil from "../../util/axiosUtil";
import basicComponent from "../../mixin/basicComponent";
import view from "../../mixin/view";
import shared from "../../util/shared";

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
                admin: false,
                viewCopy: ""
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
                let itemId = "VEHICLES_VIEW";
                let lang = this.getLang();
                if (this.cache) {
                    let cachedView = this.findViewInCache(itemId, lang, "-");
                    if (!shared.isEmpty(cachedView)) {
                        this.dispatchCachedView(cachedView, lang);
                        return cachedView;
                    }
                }
                axios
                    .get(this.basicUrl
                        + "/" + "item/view"
                        + "/" + "home"
                        + "/" + userUtil.getUserName()
                        + "/" + lang, {
                        headers: {
                            Authorization: axiosUtil.getAuthorization()
                        }
                    })
                    .then(response => {
                        let vehiclesView = response.data;
                        itemViewUtil.dispatchView(vehiclesView, lang);
                        if (this.cache) {
                            vehiclesView.reportType = "-";
                            this.cachedViews.push(vehiclesView);
                        }
                        console.log("home displayed");
                    })
                    .catch(error => {
                        itemViewUtil.dispatchResponseError(error);
                    });
            }
        }
    }
</script>

<style scoped>
</style>