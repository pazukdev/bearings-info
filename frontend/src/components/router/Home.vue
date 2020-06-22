<template>
    <div>
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
            // clearCache() {
            //     storeUtil.setCachedViews([]);
            // },

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
            },

            setCachedImg() {
                console.log("setCachedImg");
                if (shared.isEmpty(this.viewCopy.cachedImg)) {
                    let c = document.createElement('canvas');
                    let img = document.getElementById('app-img');
                    console.log(img);
                    c.height = img.naturalHeight;
                    c.width = img.naturalWidth;
                    let ctx = c.getContext('2d');
                    ctx.drawImage(img, 0, 0, c.width, c.height);
                    this.viewCopy.cachedImg = c.toDataURL();
                    this.cachedViews.push(this.viewCopy);
                }
            }

        }
    }
</script>

<style scoped>

</style>